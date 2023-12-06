package com.example.motimates

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motimates.databinding.ActivityAchievementBinding
import com.example.motimates.databinding.ActivityAchievementMainBinding
import com.example.motimates.databinding.ActivityGoalListBinding
import com.google.android.material.navigation.NavigationView

class AchievementMain : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement_main)

        val binding = ActivityAchievementMainBinding.inflate(layoutInflater)
        val binding2 = ActivityGoalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 목표리스트 리사이클러뷰 출력
        val achList= mutableListOf<String>()
        val achText1= binding2.goalTitleTextView1
        val achDetail1 = binding2.goalDetailsTextView1

        val achText2= binding2.goalTitleTextView2
        val achDetail2 = binding2.goalDetailsTextView2

        val achText3= binding2.goalTitleTextView3
        val achDetail3 = binding2.goalDetailsTextView3

        val achText4= binding2.goalTitleTextView4
        val achDetail4 = binding2.goalDetailsTextView4//목표 리스트 받아오기 (하드코딩)

        achList.add(achText1.text.toString())
        achList.add(achText2.text.toString())
        achList.add(achText3.text.toString())
        achList.add(achText4.text.toString()) //목표 리스트 출력(하드코딩)

        binding.recyclerAch.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerAch.adapter= AchAdapter(achList) //목표 리스트 리사이클러 뷰 어댑터 연결


        //꽃 리스트 리사이클러뷰 출력
        val flowerList= mutableListOf<Int>(R.drawable.logo, R.drawable.logo, R.drawable.logo) //꽃 리스트(하드코딩)
        binding.recyclerFlower.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerFlower.adapter= FlowerAdapter(flowerList) //꽃 리스트 리사이클러 뷰 어댑터 연결

        //꽃밭으로 가기 버튼 이벤트 처리
        binding.gardenButton.setOnClickListener {
            val intent= Intent(this, MyGarden::class.java)
            startActivity(intent)
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}