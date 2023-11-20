package com.example.motimates

// MyPageActivity.kt
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // 프로필 이미지 및 텍스트 설정
        profileImageView.setImageResource(R.drawable.default_profile_image)
        greetingTextView.text = "안녕하세요, 회원이름님!"

        // 목표 정보 설정 (가상의 데이터로 예시)
        waterDropCountTextView.text = "5"
        flowerCountTextView.text = "10"
        goalCountTextView.text = "3"

        // 목표 추가하기 버튼 클릭 이벤트
        addGoalButton.setOnClickListener {
            // 목표 추가하기 버튼 클릭 시 처리
            val intent = Intent(this, Achievement::class.java)
            startActivity(intent)
        }

        // 달성 중인 목표 보기 버튼 클릭 이벤트
        viewGoalsButton.setOnClickListener {
            // 달성 중인 목표 보기 버튼 클릭 시 처리
            val intent = Intent(this, AchievementMain::class.java)
            startActivity(intent)
        }

        // 나의 꽃밭 보기 버튼 클릭 이벤트
        viewFlowerGardenButton.setOnClickListener {
            // 나의 꽃밭 보기 버튼 클릭 시 처리
            val intent = Intent(this, MyGarden::class.java)
            startActivity(intent)
        }

        // 회원 정보 수정 버튼 클릭 이벤트
        editProfileButton.setOnClickListener {
            // 회원 정보 수정 버튼 클릭 시 처리
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
