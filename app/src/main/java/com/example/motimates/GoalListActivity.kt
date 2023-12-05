package com.example.motimates

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class GoalListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_list)

        // 목표를 누를 때의 이벤트 처리
        val goalLayout = findViewById<LinearLayout>(R.id.goalLayout1) // 목표 레이아웃 ID

        goalLayout.setOnClickListener {
            // 선택한 목표의 정보
            val selectedGoalTitle = "물 2L 이상 마시기"
            val selectedGoalDetails = "- 30 days challenge\n- 매일 오전 8시마다, 팝업"

            // 목표 상세 화면으로 이동하는 인텐트 생성
            val intent = Intent(this, ActivityInProgressGoalActivity::class.java)
            intent.putExtra("goalTitle", selectedGoalTitle)
            intent.putExtra("goalDetails", selectedGoalDetails)

            // 목표 상세 화면으로 이동
            startActivity(intent)
        }
    }
}