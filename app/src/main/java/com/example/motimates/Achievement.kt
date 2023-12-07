package com.example.motimates

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motimates.databinding.ActivityAchievementBinding
import com.google.android.material.navigation.NavigationView
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Achievement : AppCompatActivity() {
    var uploaded = 0 //사진이 업로드 되었는지 확인하는 변수
    override fun onCreate(savedInstanceState: Bundle?) {

        val binding = ActivityAchievementBinding.inflate(layoutInflater) //oncreate 외부에서도 뷰바인딩 사용
        lateinit var filePath: String

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        setContentView(binding.root)

        //갤러리 앱 연동
        val requestGalleryLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                val calRatio= calculateInSampleSize(it.data?.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize))
                val option= BitmapFactory.Options()
                option.inSampleSize= calRatio

                //이미지 로딩
                var inputStream= contentResolver.openInputStream(it.data?.data!!)
                val bitmap= BitmapFactory.decodeStream(inputStream, null, option)
                inputStream?.close()
                inputStream = null

                bitmap?.let {
                    binding.proofImage.setImageBitmap(bitmap)
                }?: let{
                    Log.d("로그처리", "이미지 로딩 실패")
                }
            }catch (e: Exception){
                e.printStackTrace()
            } // 예외 발생시 throw

            dialog() //인증 완료 다이얼로그
        }

        //카메라 앱 연동
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
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

            dialog() //인증 완료 다이얼로그
        }

        //카메라 버튼
        binding.cameraButton.setOnClickListener {
            Log.d("로그처리", "카메라 선택")
            uploaded+= 1 //사진 업로드 확인
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
            uploaded+= 1 //사진 업로드 확인
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val title= findViewById<TextView>(R.id.titleTextView)
        val target= findViewById<TextView>(R.id.target_content)

        val selectedTitle = intent.getStringExtra("goalTitle")
        title.setText(selectedTitle) //인텐트로 제목 받아오기
        var detail = intent.getStringExtra("goalDetails")
        if (detail != null) {
            if(detail.contains(",")) {
                detail= detail.replace(",", "\n-") //세부사항에 ,가 있으면 줄바꿈으로 변경
            }
            target.setText(detail)
        } //인텐트로 세부사항 받아오기

        val today= findViewById<TextView>(R.id.dateTextView)
        val current= Calendar.getInstance().getTime()
        val formatter= SimpleDateFormat("yyyy년 MM월 dd일")
        val formattedDate= formatter.format(current)
        today.setText(formattedDate) //오늘 날짜 텍스트뷰에 출력

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    //이미지 크기 계산 함수
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int) :Int {
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
                .setNegativeButton("수정하기", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }else if(uploaded>1){
            AlertDialog.Builder(this)
                .setTitle("수정 완료")
                .setMessage("인증사진이 수정되었습니다.")
                .setPositiveButton("목록으로 돌아가기", DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this, GoalListActivity::class.java)
                    startActivity(intent)
                })
                .setNegativeButton("수정하기", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }
    }
}