package com.example.motimates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityInProgressGoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inprogress_goal)

        // 전달된 데이터 받기
        val selectedGoalTitle = intent.getStringExtra("goalTitle")
        val selectedGoalDetails = intent.getStringExtra("goalDetails")

        // 받아온 목표 정보를 화면에 표시
//        goalTitleTextView.text = selectedGoalTitle
//        goalDetailsTextView.text = selectedGoalDetails
    }
}
