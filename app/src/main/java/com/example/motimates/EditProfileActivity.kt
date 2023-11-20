package com.example.motimates

// EditProfileActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // 기존 회원 정보 불러와서 화면에 설정 (가상의 데이터로 예시)
        val currentName = "현재 이름"
        val currentNickname = "현재 닉네임"
        val currentEmail = "현재 이메일@example.com"
        val currentPhoneNumber = "010-1234-5678"

        nameEditText.setText(currentName)
        nicknameEditText.setText(currentNickname)
        emailEditText.setText(currentEmail)
        phoneNumberEditText.setText(currentPhoneNumber)

        // 수정 버튼 클릭 이벤트
        editButton.setOnClickListener {
            enableEditMode()
        }

        // 저장 버튼 클릭 이벤트
        saveButton.setOnClickListener {
            // 수정된 정보 저장
            val editedName = nameEditText.text.toString()
            val editedNickname = nicknameEditText.text.toString()
            val editedEmail = emailEditText.text.toString()
            val editedPhoneNumber = phoneNumberEditText.text.toString()

            // 여기에서 수정된 정보를 저장 또는 전송하는 작업을 수행하면 됩니다.
            // (예: 서버에 전송, 로컬 데이터베이스에 저장 등)

            // 수정이 완료되면 다시 읽기 전용 모드로 변경
            disableEditMode()

            // 뷰 갱신
            nameEditText.setText(editedName)
            nicknameEditText.setText(editedNickname)
            emailEditText.setText(editedEmail)
            phoneNumberEditText.setText(editedPhoneNumber)
        }

        // 취소 버튼 클릭 이벤트
        cancelButton.setOnClickListener {
            // 수정이 취소되면 읽기 전용 모드로 변경
            disableEditMode()

            // 이전 데이터로 다시 설정
            nameEditText.setText(currentName)
            nicknameEditText.setText(currentNickname)
            emailEditText.setText(currentEmail)
            phoneNumberEditText.setText(currentPhoneNumber)
        }

        // 초기에는 읽기 전용 모드로 시작
        disableEditMode()
    }

    private fun enableEditMode() {
        // 수정 버튼 클릭 시 호출되며, 편집 가능한 상태로 변경
        nameEditText.isEnabled = true
        nicknameEditText.isEnabled = true
        emailEditText.isEnabled = true
        phoneNumberEditText.isEnabled = true

        // 버튼 상태 변경
        editButton.isEnabled = false
        saveButton.isEnabled = true
        cancelButton.isEnabled = true
    }

    private fun disableEditMode() {
        // 저장 또는 취소 버튼 클릭 시 호출되며, 읽기 전용 상태로 변경
        nameEditText.isEnabled = false
        nicknameEditText.isEnabled = false
        emailEditText.isEnabled = false
        phoneNumberEditText.isEnabled = false

        // 버튼 상태 변경
        editButton.isEnabled = true
        saveButton.isEnabled = false
        cancelButton.isEnabled = false
    }
}
