package com.example.motimates

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motimates.R
import com.google.android.material.navigation.NavigationView

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    //private lateinit var nicknameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 1
    }
    private fun saveProfile() {
        // 사용자가 입력한 값을 가져와서 저장하는 로직 추가
        val newName = nameEditText.text.toString()
        //val newNickname = nicknameEditText.text.toString()
        val newEmail = emailEditText.text.toString()
        val newPhoneNumber = phoneNumberEditText.text.toString()

        // SharedPreferences를 사용하여 데이터 저장
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", newName)
        //editor.putString("nickname", newNickname)
        editor.putString("email", newEmail)
        editor.putString("phoneNumber", newPhoneNumber)
        editor.apply()

        // 메인 페이지로 돌아갈 때 데이터 전달
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("newName", newName)
        startActivity(intent)

        showToast("프로필이 저장되었습니다.")
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize your views
        nameEditText = findViewById(R.id.nameEditText)
        //nicknameEditText = findViewById(R.id.nicknameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)


        saveButton.setOnClickListener {
            val message = "프로필이 저장되었습니다."
            showToast(message)
            saveProfile()
            finish()
        }

        cancelButton.setOnClickListener {
            val message = "수정이 취소되었습니다."
            showToast(message)
            finish() // 메인 화면으로 돌아감
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

