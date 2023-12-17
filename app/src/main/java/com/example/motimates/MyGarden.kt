package com.example.motimates

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.motimates.databinding.ActivityMyGardenBinding
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class MyGarden : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_garden)

        val binding= ActivityMyGardenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager= GridLayoutManager(this, 3)
        binding.recyclerview.adapter= FlowerAdapter(2) //꽃 리스트 개수 하드코딩(...)


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
}