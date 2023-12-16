package com.example.motimates

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class ActivityInProgressGoalActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inprogress_goal)

        // Intent로부터 목표 정보 받아오기
        val goalTitle = intent.getStringExtra("goalTitle")
        val goalDetails = intent.getStringExtra("goalDetails")

        // 받아온 목표 정보를 화면에 출력
        val titleTextView = findViewById<TextView>(R.id.goalTitleTextView)
        val detailsTextView = findViewById<TextView>(R.id.goalDetailsTextView)

        titleTextView.text = goalTitle
        detailsTextView.text = goalDetails

//        추가된 부분!!!(시작)
        //인증시 도장 출력
        val mytable = findViewById<TableLayout>(R.id.tableLayout)
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("SELECT distinct TodayGoalList.date FROM TodayGoalList WHERE goal = '$goalTitle'", null)
        var count=0
        if(cursor !=null && cursor.moveToFirst()){
            do{
                Log.d("로그처리", "저장된 데이터 존재 : ${cursor.getString(0)}") //로그처리, 수행한 날짜를 확인
                val row= mytable[0] as TableRow //추후 확장(count%6==0일 경우 줄바꿈)
                val cell = row[count++] as ImageView
                cell.setImageResource(R.drawable.success)
                val text= cursor.getString(0)
                cell.setOnClickListener(){
                    Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
                } //클릭시 토스트 메세지로 날짜 출력
            }while(cursor.moveToNext())
            db.close()
        }
//        추가된 부분!!!(끝)

        // 목표 인증하러 가기 버튼
        val certificationButton = findViewById<Button>(R.id.certificationButton)
        certificationButton.setOnClickListener {
            // Achievement.kt 액티비티로 이동
            val intent = Intent(this, Achievement::class.java)
            intent.putExtra("goalTitle", goalTitle)
            intent.putExtra("goalDetails", goalDetails)
            startActivity(intent)
        }

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

