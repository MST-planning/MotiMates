package com.example.motimates

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
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

        //네비게이션, 툴바
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawer, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        //목표 기간 설정
        binding.period.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.month -> {
                    binding.customText.visibility = View.GONE
                    calendar.add(Calendar.DAY_OF_MONTH, 30)
                }
                R.id.year -> {
                    binding.customText.visibility = View.GONE
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
                R.id.every -> {
                    binding.customText.visibility = View.GONE
                    calendar.add(Calendar.DAY_OF_YEAR, 10)
                }
                R.id.custom -> {
                    DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show()
                } } }

        //저장 버튼 -> 메인 엑티비티로 이동
        binding.save.setOnClickListener{
            //알림 테스트
            //알림테스트 끝
            save()
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
            TimePickerDialog(this, android.R.style.Theme_Holo_Dialog, time, 12, 0,true).show()
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

    private fun save()
    {
        val purpose = Purpose(binding.title.toString(), binding.content.toString(), adapter.currentList.toList(), calendar)
        Purpose.commit(purpose)
    }
}