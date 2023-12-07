package com.example.motimates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Logout : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        val logoutButton: Button = findViewById(R.id.logoutButton)
        val cancelButton: Button = findViewById(R.id.logoutCancelButton)

        logoutButton.setOnClickListener {
            // 로그아웃 수행
            FirebaseAuth.getInstance().signOut()

            // 토스트 메시지 표시
            Toast.makeText(
                this@Logout,
                "로그아웃 되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            // 로그인 페이지로 이동
            val intent = Intent(this@Logout, Signin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            // 현재 액티비티 종료
            finish()
        }

        cancelButton.setOnClickListener {
            // 메인 페이지로 이동
            val intent = Intent(this@Logout, MainActivity::class.java)
            startActivity(intent)

            // 현재 액티비티 종료
            finish()
        }
    }
}
