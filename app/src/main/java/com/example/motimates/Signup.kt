package com.example.motimates

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Signup : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_signup)

        // 가입하기 버튼
        val buttonSignup: Button = findViewById(R.id.buttonSignUp)
        buttonSignup.setOnClickListener {
            val emailEditText: EditText = findViewById(R.id.signupEmail)
            val passwordEditText: EditText = findViewById(R.id.signupPassword)
            val nicknameEditText : EditText = findViewById(R.id.signupNickname)
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val nickname = nicknameEditText.text.toString()
            createAccount(email, password, nickname)
        }

        // 취소하기 버튼
        val cancelButton: Button = findViewById(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            finish() // 액티비티 종료
        }
    }
    private fun createAccount(email: String, password: String, nickname: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        saveNickname(nickname)
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    private fun saveNickname(nickname: String) {
        // SharedPreferences 등을 사용하여 닉네임 저장
        val preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("nickname", nickname)
        editor.apply()
    }
}