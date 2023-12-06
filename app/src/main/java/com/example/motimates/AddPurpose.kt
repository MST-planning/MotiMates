package com.example.motimates

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.example.motimates.databinding.AddPurposeBinding
import java.util.Calendar

class AddPurpose : AppCompatActivity() {
    //목표 종료 일자
    private var calendar = Calendar.getInstance()
    //해당 목표에 대한 알림 시간 리스트
    private var times = mutableListOf<List<Int>>()
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

        binding.save.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.addTime.setOnClickListener{
            //화면에 timePicker 생성
            TimePickerDialog(this, time, 12, 0,true).show()

            //선택한 시간 목록 보여주기
        }
    }

    private val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        datetext = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DATE)}일까지"
    }

    private val time = TimePickerDialog.OnTimeSetListener{ view, hour, minute ->
        val t = listOf<Int>(hour, minute)

        times.add(t)
    }
}