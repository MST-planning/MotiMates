package com.example.motimates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motimates.databinding.ActivityAchievementMainBinding

class AchievementMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement_main)

        val binding = ActivityAchievementMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 목표리스트 리사이클러뷰 출력
        val achList= mutableListOf<String>("목표1", "목표2", "목표3") //목표 리스트(하드코딩)
        binding.recyclerAch.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerAch.adapter= AchAdapter(achList) //목표 리스트 리사이클러 뷰 어댑터 연결
        binding.recyclerAch.setOnClickListener(){
            val intent= Intent(this, Achievement::class.java)
            startActivity(intent) //목표 리스트 리사이클러 뷰 클릭 이벤트 처리(목표 수행 페이지로 이동)
        }

        //꽃 리스트 리사이클러뷰 출력
        val flowerList= mutableListOf<Int>(R.drawable.logo, R.drawable.logo, R.drawable.logo) //꽃 리스트(하드코딩)
        binding.recyclerFlower.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerFlower.adapter= FlowerAdapter(flowerList) //꽃 리스트 리사이클러 뷰 어댑터 연결

        //꽃밭으로 가기 버튼 이벤트 처리
        binding.gardenButton.setOnClickListener {
            val intent= Intent(this, MyGarden::class.java)
            startActivity(intent)
        }
    }
}