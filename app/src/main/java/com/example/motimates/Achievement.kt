package com.example.motimates

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motimates.databinding.ActivityAchievementBinding
import com.google.android.material.navigation.NavigationView
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Achievement : AppCompatActivity() {
    var uploaded = 0 //사진이 업로드 되었는지 확인하는 변수

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {

        val binding = ActivityAchievementBinding.inflate(layoutInflater) //oncreate 외부에서도 뷰바인딩 사용
        lateinit var filePath: String

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        setContentView(binding.root)

        val title = findViewById<TextView>(R.id.titleTextView)
        val target = findViewById<TextView>(R.id.target_content)

        val selectedTitle = intent.getStringExtra("goalTitle")
        title.setText(selectedTitle) //인텐트로 제목 받아오기
        var detail = intent.getStringExtra("goalDetails")
        if (detail != null) {
            if (detail.contains(",")) {
                detail = detail.replace(",", "\n-") //세부사항에 ,가 있으면 줄바꿈으로 변경
            }
            target.setText(detail)
        } //인텐트로 세부사항 받아오기

        val today = findViewById<TextView>(R.id.dateTextView)
        val current = Calendar.getInstance().getTime()
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        val formattedDate = formatter.format(current)

        val formatter2 = SimpleDateFormat("yyyyMMdd")
        val formattedDate2 = formatter2.format(current)
        today.setText(formattedDate) //오늘 날짜 텍스트뷰에 출력

        //ㅅㅏ진이 있다면 출력
        val db = DBHelper(this).readableDatabase
        val cursor= db.rawQuery("select * from TodayGoalList " +
                "where TodayGoalList.date='$formattedDate2' and TodayGoalList.Goal='$selectedTitle'", null)
        if (cursor != null && cursor.moveToFirst()) {
            val image = cursor.getBlob(cursor.getColumnIndex("image"))
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            binding.proofImage.setImageBitmap(bitmap)
            Log.d("로그처리", "이미지 로딩 성공!!!!!!")
            db.close()
        }

        //갤러리 앱 연동
        val requestGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                try {
                    val calRatio = calculateInSampleSize(
                        it.data?.data!!,
                        resources.getDimensionPixelSize(R.dimen.imgSize),
                        resources.getDimensionPixelSize(R.dimen.imgSize)
                    )
                    val option = BitmapFactory.Options()
                    option.inSampleSize = calRatio

                    //이미지 로딩
                    var inputStream = contentResolver.openInputStream(it.data?.data!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                    inputStream?.close()
                    inputStream = null

                    bitmap?.let {
                        binding.proofImage.setImageBitmap(bitmap)

                        //db에 이미지와 내용 저장
                        val byteArrayInputStream= ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream)
                        val imageinByte: ByteArray = byteArrayInputStream.toByteArray()

                        val values= ContentValues().apply{
                            put("goal", selectedTitle)
                            put("date", formattedDate2)
                            put("image", imageinByte)
                        }

                        val db = DBHelper(this).writableDatabase
                        db.insert("TodayGoalList", null, values)
                        Log.d("로그처리", "저장성공(갤러리): $selectedTitle-$formattedDate2")
                        db.close()
                    } ?: let {
                        Log.d("로그처리", "이미지 로딩 실패")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } // 예외 발생시 throw
                dialog() //인증 완료 다이얼로그
            }

        //카메라 앱 연동
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                binding.proofImage.setImageBitmap(bitmap)
            }

            //이미지와 내용을 저장
            val byteArrayInputStream= ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream)
            val imageinByte: ByteArray = byteArrayInputStream.toByteArray()

            val values= ContentValues().apply{
                put("goal", selectedTitle)
                put("date", formattedDate2)
                put("image", imageinByte)
            }

            val db = DBHelper(this).writableDatabase
            db.insert("TodayGoalList", null, values)
            Log.d("로그처리", "저장성공(카메라): $selectedTitle-$formattedDate2")
            db.close()

            dialog() //인증 완료 다이얼로그
        }

        //카메라 버튼
        binding.cameraButton.setOnClickListener {
            Log.d("로그처리", "카메라 선택")
            uploaded += 1 //사진 업로드 확인
            val timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
            filePath = file.absolutePath
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.motimates.fileprovider",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)
        }

        //갤러리 버튼
        binding.galleryButton.setOnClickListener {
            Log.d("로그처리", "갤러리 선택")
            uploaded += 1 //사진 업로드 확인
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main_page -> {
                    // 홈 화면으로 이동입
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.add_ach -> {
                    // 목표 추가 화면으로 이동
                    startActivity(Intent(this, AddPurpose::class.java))
                    true
                }
                R.id.look_ach -> {
                    // 목표 보기 화면으로 이동
                    startActivity(Intent(this, GoalListActivity::class.java))
                    true
                }
                R.id.look_garden-> {
                    // 꽃밭 보기 화면으로 이동
                    startActivity(Intent(this, MyGarden::class.java))
                    true
                }
                R.id.member_info-> {
                    // 회원 정보 수정 화면으로 이동
                    startActivity(Intent(this, EditProfileActivity::class.java))
                    true
                }
                R.id.signup -> {
                    startActivity(Intent(this, Signup::class.java))
                    true
                }
                R.id.signin -> {
                    startActivity(Intent(this, Signin::class.java))
                    true
                }
                R.id.logout -> {
                    startActivity(Intent(this, Logout::class.java))
                    true
                }
                else -> false
            }
        }
    }

    //이미지 크기 계산 함수
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }// 예외 발생시 throw
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1 //이미지 크기를 줄이기 위한 변수
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun dialog() {
        if (uploaded == 1) {
            AlertDialog.Builder(this)
                .setTitle("인증 완료")
                .setMessage("오늘도 한 걸음 목표에 가까워졌네요^^")
                .setPositiveButton("목록으로 돌아가기", DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this, GoalListActivity::class.java)
                    startActivity(intent)
                })
                .show()
        } else if (uploaded > 1) {
            AlertDialog.Builder(this)
                .setTitle("수정 완료")
                .setMessage("인증사진이 수정되었습니다.")
                .setPositiveButton("목록으로 돌아가기", DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this, GoalListActivity::class.java)
                    startActivity(intent)
                })
                .show()
        }
    }
}