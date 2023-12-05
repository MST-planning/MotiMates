package com.example.motimates

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motimates.MyGarden
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var greetingTextView: TextView
    private lateinit var greetingTextView2: TextView
    private lateinit var waterDropCountTextView: TextView
    private lateinit var flowerCountTextView: TextView
    private lateinit var goalCountTextView: TextView
    private lateinit var addGoalButton: Button
    private lateinit var viewGoalsButton: Button
    private lateinit var viewFlowerGardenButton: Button
    private lateinit var editProfileButton: Button

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize your views
        profileImageView = findViewById(R.id.profileImageView)
        greetingTextView = findViewById(R.id.greetingTextView)
        greetingTextView2 = findViewById(R.id.greetingTextView2)
        waterDropCountTextView = findViewById(R.id.waterDropCountTextView)
        flowerCountTextView = findViewById(R.id.flowerCountTextView)
        goalCountTextView = findViewById(R.id.goalCountTextView)
        addGoalButton = findViewById(R.id.addGoalButton)
        viewGoalsButton = findViewById(R.id.viewGoalsButton)
        viewFlowerGardenButton = findViewById(R.id.viewFlowerGardenButton)
        editProfileButton = findViewById(R.id.editProfileButton)

        // Set click listeners for buttons
        addGoalButton.setOnClickListener {
            // Handle the click event for the "목표 추가하기" button
            // For example, open a new activity to add a goal
            startActivity(Intent(this, AddPurpose::class.java))
        }

        viewGoalsButton.setOnClickListener {
            // Handle the click event for the "달성 중인 목표 보기" button
            // For example, open a new activity to view goals in progress
            startActivity(Intent(this, GoalListActivity::class.java))
        }

        viewFlowerGardenButton.setOnClickListener {
            // Handle the click event for the "나의 꽃밭 보기" button
            // For example, open a new activity to view the flower garden
            startActivity(Intent(this, MyGarden::class.java))
        }

        editProfileButton.setOnClickListener {
            // Handle the click event for the "회원 정보 수정" button
            // For example, open a new activity to edit the user's profile
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

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