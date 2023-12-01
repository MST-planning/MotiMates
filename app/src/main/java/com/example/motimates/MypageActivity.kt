package com.example.motimates

// MyPageActivity.kt
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motimates.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 프로필 이미지 및 텍스트 설정
        binding.profileImageView.setImageResource(R.drawable.default_profile_image)
        binding.greetingTextView.text = "안녕하세요, 회원이름님!"

        // 목표 정보 설정 (가상의 데이터로 예시)
        binding.waterDropCountTextView.text = "5"
        binding.flowerCountTextView.text = "10"
        binding.goalCountTextView.text = "3"

        // 목표 추가하기 버튼 클릭 이벤트
        binding.addGoalButton.setOnClickListener {
            // 목표 추가하기 버튼 클릭 시 처리
            val intent = Intent(this, AchievementMain::class.java)
            startActivity(intent)
        }

        // 달성 중인 목표 보기 버튼 클릭 이벤트
        binding.viewGoalsButton.setOnClickListener {
            // 달성 중인 목표 보기 버튼 클릭 시 처리
            val intent = Intent(this, AddPurpose::class.java)
            startActivity(intent)
        }

        // 나의 꽃밭 보기 버튼 클릭 이벤트
        binding.viewFlowerGardenButton.setOnClickListener {
            // 나의 꽃밭 보기 버튼 클릭 시 처리
            val intent = Intent(this, MyGarden::class.java)
            startActivity(intent)
        }

        // 회원 정보 수정 버튼 클릭 이벤트
        binding.editProfileButton.setOnClickListener {
            // 회원 정보 수정 버튼 클릭 시 처리
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
