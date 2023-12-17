package com.example.motimates

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motimates.EditProfileActivity.Companion.EDIT_PROFILE_REQUEST_CODE
import com.example.motimates.MyGarden
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

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
    private lateinit var logoutButton : Button

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onResume() {
        super.onResume()
        // EditProfileActivity에서 돌아왔을 때, 데이터 확인
        val newName = intent.getStringExtra("newName")
        if (!newName.isNullOrBlank()) {
            greetingTextView2.text = newName + "님!"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

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

        notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager

        updateWelcomeMessage()
        permissionCheck()
        makeChannel()
        notify("일단제목", "일단 내용")

        addGoalButton.setOnClickListener {
            startActivity(Intent(this, AddPurpose::class.java))
        }

        viewGoalsButton.setOnClickListener {
            startActivity(Intent(this, GoalListActivity::class.java))
        }

        viewFlowerGardenButton.setOnClickListener {
            startActivity(Intent(this, MyGarden::class.java))
        }

        editProfileButton.setOnClickListener {
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
    private fun updateWelcomeMessage() {
        val preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val nickname = preferences.getString("nickname", "")
        greetingTextView2.text = "$nickname 님!"
    }

//  채널 설정
    private val channelId = "stateBar"
    private val channelName = "상태바 알림"
    private val channelDescription = "목표 수행을 재촉하는 알림을 보냅니다."

    lateinit var notificationManager : NotificationManager

    private fun makeChannel() {
        Log.d("notification", "create NotifyChannel")

        //채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelDescription

            notificationManager.createNotificationChannel(channel)}
    }

    fun notify(title: String, content:String)
    {
        //빌더 생성
        lateinit var builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            builder = NotificationCompat.Builder(this, channelId)
        else
            builder = NotificationCompat.Builder(this)

        builder.setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(content)

        notificationManager.notify(11, builder.build())

        Log.d("일단", "실행은 되니")
    }

    private fun permissionCheck()
    {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        {
            if (it.all{permission -> !permission.value})
                Toast.makeText(this, "앱 설정 > 권한에서 알림 권한을 허용해주세요", Toast.LENGTH_LONG).show()}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ContextCompat.checkSelfPermission(this, "manifest.permission.POST_NOTIFICATIONS")
                == PackageManager.PERMISSION_DENIED)
                permissionLauncher.launch(
                    arrayOf("manifest.permission.POST_NOTIFICATION")
                )

        }
    }
}