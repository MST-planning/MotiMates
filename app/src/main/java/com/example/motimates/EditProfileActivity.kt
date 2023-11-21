package com.example.motimates

// EditProfileActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motimates.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 기존 회원 정보 불러와서 화면에 설정 (가상의 데이터로 예시)
        val currentName = "현재 이름"
        val currentNickname = "현재 닉네임"
        val currentEmail = "현재 이메일@example.com"
        val currentPhoneNumber = "010-1234-5678"

        binding.nameEditText.setText(currentName)
        binding.nicknameEditText.setText(currentNickname)
        binding.emailEditText.setText(currentEmail)
        binding.phoneNumberEditText.setText(currentPhoneNumber)

        // 수정 버튼 클릭 이벤트
        binding.editButton.setOnClickListener {
            enableEditMode()
        }

        // 저장 버튼 클릭 이벤트
        binding.saveButton.setOnClickListener {
            // 수정된 정보 저장
            val editedName = binding.nameEditText.text.toString()
            val editedNickname = binding.nicknameEditText.text.toString()
            val editedEmail = binding.emailEditText.text.toString()
            val editedPhoneNumber = binding.phoneNumberEditText.text.toString()

            // 여기에서 수정된 정보를 저장 또는 전송하는 작업을 수행하면 됩니다.
            // (예: 서버에 전송, 로컬 데이터베이스에 저장 등)

            // 수정이 완료되면 다시 읽기 전용 모드로 변경
            disableEditMode()

            // 뷰 갱신
            binding.nameEditText.setText(editedName)
            binding.nicknameEditText.setText(editedNickname)
            binding.emailEditText.setText(editedEmail)
            binding.phoneNumberEditText.setText(editedPhoneNumber)
        }

        // 취소 버튼 클릭 이벤트
        binding.cancelButton.setOnClickListener {
            // 수정이 취소되면 읽기 전용 모드로 변경
            disableEditMode()

            // 이전 데이터로 다시 설정
            binding.nameEditText.setText(currentName)
            binding.nicknameEditText.setText(currentNickname)
            binding.emailEditText.setText(currentEmail)
            binding.phoneNumberEditText.setText(currentPhoneNumber)
        }

        // 초기에는 읽기 전용 모드로 시작
        disableEditMode()
    }

    private fun enableEditMode() {
        // 수정 버튼 클릭 시 호출되며, 편집 가능한 상태로 변경
        binding.nameEditText.isEnabled = true
        binding.nicknameEditText.isEnabled = true
        binding.emailEditText.isEnabled = true
        binding.phoneNumberEditText.isEnabled = true

        // 버튼 상태 변경
        binding.editButton.isEnabled = false
        binding.saveButton.isEnabled = true
        binding.cancelButton.isEnabled = true
    }

    private fun disableEditMode() {
        // 저장 또는 취소 버튼 클릭 시 호출되며, 읽기 전용 상태로 변경
        binding.nameEditText.isEnabled = false
        binding.nicknameEditText.isEnabled = false
        binding.emailEditText.isEnabled = false
        binding.phoneNumberEditText.isEnabled = false

        // 버튼 상태 변경
        binding.editButton.isEnabled = true
        binding.saveButton.isEnabled = false
        binding.cancelButton.isEnabled = false
    }
}
