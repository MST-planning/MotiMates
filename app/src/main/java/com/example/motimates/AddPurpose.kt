package com.example.motimates

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.motimates.databinding.AddPurposeBinding
import java.util.Calendar

class AddPurpose : AppCompatActivity() {

    private var calendar = Calendar.getInstance()
    var datetext = "string"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AddPurposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.period.setOnCheckedChangeListener { group, checkedId ->
            //라디오 버튼 선택
            when(checkedId)
            {
                R.id.month -> {
                    binding.customText.visibility = View.GONE
                    //한달짜리 목표가 됩니다.
                }
                R.id.year -> {
                    binding.customText.visibility = View.GONE
                    //일년
                }
                R.id.every -> {
                    binding.customText.visibility = View.GONE
                    //매일
                }
                R.id.custom -> {
                    //DateaPicker 화면에 생성 -> calendar과 datetext에 선택 날짜 저장
                    DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show()

                    //선택한 날짜 텍스트로 보여주기
                    binding.customText.visibility = View.VISIBLE
                    binding.customText.text = "${datetext}까지"
                }
            }
        }
    }

    private val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        datetext = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DATE)}일까지"
    }
}