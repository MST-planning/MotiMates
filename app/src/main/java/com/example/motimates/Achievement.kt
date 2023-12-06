package com.example.motimates

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.motimates.databinding.ActivityAchievementBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class Achievement : AppCompatActivity() {
    private var binding = ActivityAchievementBinding.inflate(layoutInflater) //oncreate 외부에서도 뷰바인딩 사용
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)

        // 뷰 바인딩 초기화
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //사진 올리기 버튼
        binding.uploadButton.setOnClickListener{
            //이미지 업로드 버튼 이벤트 처리
            val options= arrayOf<String>("갤러리에서 선택하기", "카메라에서 촬영하기")
            AlertDialog.Builder(this).run{
                setTitle("업로드할 이미지 선택")
                setItems(options){_, which->
                    Log.d("로그처리", "이미지 업로드")
                    when(which){
                        0-> openGallery()
                        1-> openCamera()
                    }
                }
                setPositiveButton("닫기", null)
                show()
            } // 이후에 목표 수행을 true로 바꾸기?
        }

        //사진 수정 버튼
        if (binding.proofImage != null){
            binding.modifyButton.isEnabled= true
        }
        binding.modifyButton.setOnClickListener{
            //이미지 수정 버튼 이벤트 처리
            val options= arrayOf<String>("갤러리에서 선택하기", "카메라에서 촬영하기")
            AlertDialog.Builder(this).run{
                setTitle("업로드할 이미지 선택")
                setItems(options){_, which->
                    Log.d("로그처리", "이미지 수정")
                    when(which){
                        0-> openGallery()
                        1-> openCamera()
                    }
                }
                setPositiveButton("닫기", null)
                show()
            }
        }
    }

    //갤러리 앱 연동하기
    private val requestGalleryLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        try {
            val calRatio= calculateIsSampleSize(it.data?.data!!,
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
    }

    //갤러리 인탠트 실행
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requestGalleryLauncher.launch(intent) //gIntent: 갤러리 인텐트
    }


    //카메라 앱 연동하기
    //카메라 인텐트 실행
    private fun openCamera() {
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

    //비트맵 이미지 생성
    private val requestCameraFileLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val calRatio = calculateIsSampleSize(
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
    }

    //이미지 크기 계산 함수
    private fun calculateIsSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int) :Int {
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
}