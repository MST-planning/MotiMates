package com.example.motimates

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class GoalListActivity : AppCompatActivity(){

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_list)

        // 각 목표 항목에 대한 뷰 찾기
        val goalLayout1 = findViewById<LinearLayout>(R.id.goalLayout1)
        val goalLayout2 = findViewById<LinearLayout>(R.id.goalLayout2)
        val goalLayout3 = findViewById<LinearLayout>(R.id.goalLayout3)
        val goalLayout4 = findViewById<LinearLayout>(R.id.goalLayout4)

        val goalTitleTextView1 = findViewById<TextView>(R.id.goalTitleTextView1).text.toString()
        val goalDetailsTextView1 = findViewById<TextView>(R.id.goalDetailsTextView1).text.toString()
        val goalTitleTextView2 = findViewById<TextView>(R.id.goalTitleTextView2).text.toString()
        val goalDetailsTextView2 = findViewById<TextView>(R.id.goalDetailsTextView2).text.toString()
        val goalTitleTextView3 = findViewById<TextView>(R.id.goalTitleTextView3).text.toString()
        val goalDetailsTextView3 = findViewById<TextView>(R.id.goalDetailsTextView3).text.toString()
        val goalTitleTextView4 = findViewById<TextView>(R.id.goalTitleTextView4).text.toString()
        val goalDetailsTextView4 = findViewById<TextView>(R.id.goalDetailsTextView4).text.toString()


        // 각 목표 항목 클릭 시 호출될 메서드
        fun onGoalItemClick(goalTitle: String, goalDetails: String) {
            // 목표 세부 정보를 전달할 Intent 생성
            val intent = Intent(this, ActivityInProgressGoalActivity::class.java)
            intent.putExtra("goalTitle", goalTitle)
            intent.putExtra("goalDetails", goalDetails)

            // 새로운 화면으로 이동
            startActivity(intent)
        }

        // 각 목표 항목에 클릭 리스너 설정
        goalLayout1.setOnClickListener { onGoalItemClick(goalTitleTextView1, goalDetailsTextView1) }
        goalLayout2.setOnClickListener { onGoalItemClick(goalTitleTextView2, goalDetailsTextView2) }
        goalLayout3.setOnClickListener { onGoalItemClick(goalTitleTextView3, goalDetailsTextView3) }
        goalLayout4.setOnClickListener { onGoalItemClick(goalTitleTextView4, goalDetailsTextView4) }


        // 네비게이션 뷰를 위한 설정
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        // 네비게이션 아이템 클릭 리스너 설정
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.main_page -> {
                    // 홈 화면으로 이동
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
                else -> false
            }
        }
    }
}