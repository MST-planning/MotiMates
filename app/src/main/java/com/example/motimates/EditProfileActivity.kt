package com.example.motimates

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.motimates.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var nicknameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize your views
        nameEditText = findViewById(R.id.nameEditText)
        nicknameEditText = findViewById(R.id.nicknameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        // Set click listeners for buttons
        saveButton.setOnClickListener {
            // Handle the click event for the "저장" button
            // For example, save the profile changes to the server or database
            // Here, just show a toast message as an example
            val message = "프로필이 저장되었습니다."
            showToast(message)
        }

        cancelButton.setOnClickListener {
            // Handle the click event for the "취소" button
            // For example, go back to the previous activity
            // Here, just show a toast message as an example
            val message = "수정이 취소되었습니다."
            showToast(message)
            finish() // Close this activity and go back
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
