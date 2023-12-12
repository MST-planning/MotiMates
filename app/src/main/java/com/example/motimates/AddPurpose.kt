package com.example.motimates

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motimates.databinding.AddPurposeBinding
import java.util.Calendar

class AddPurpose : AppCompatActivity() {
    //목표 종료 일자
    private var calendar = Calendar.getInstance()
    //해당 목표에 대한 알림 시간 리스트
    private var times = mutableListOf<List<Int>>()
    private var datetext = "string"

    private lateinit var binding: AddPurposeBinding
    private lateinit var adapter: TimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddPurposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //목표 기간 설정
        binding.period.setOnCheckedChangeListener { group, checkedId ->
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
                } } }

        //저장 버튼 -> 메인 엑티비티로 이동
        binding.save.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //취소 버튼
        binding.cancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //팝업 알림 시간 추가
        binding.addTime.setOnClickListener{
            //화면에 timePicker 생성
            TimePickerDialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, time, 12, 0,true).show()
        }
        adapter = TimeAdapter()
        binding.timeRecycler.layoutManager = LinearLayoutManager(this)
        binding.timeRecycler.adapter = adapter
    }

    //종료 날짜 선택 리스너 함수
    private val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        datetext = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DATE)}일까지"

        //선택한 날짜 텍스트로 보여주기
        binding.customText.visibility = View.VISIBLE
        binding.customText.text = datetext
    }

    //팝업 알림 시간 추가 리스너 함수
    private val time = TimePickerDialog.OnTimeSetListener{view, hour, minute ->
        val t = listOf(hour, minute)
        times = adapter.currentList.toMutableList()
        times.add(t)
        //추가된 리스트로 업데이트
        adapter.submitList(times)
        //times의 원소가 1개 이상일 때만 visible로 변경
        if (times.isNotEmpty())
            binding.timeRecycler.visibility = View.VISIBLE
    }
}