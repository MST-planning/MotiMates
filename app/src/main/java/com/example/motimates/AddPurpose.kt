package com.example.motimates

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motimates.databinding.AddPurposeBinding
import java.util.Calendar

class AddPurpose : AppCompatActivity() {
    //목표 종료 일자
    private var calendar = Calendar.getInstance()
    //해당 목표에 대한 알림 시간 리스트
    private var times = mutableListOf<List<Int>>()
    var datetext = "string"

    private lateinit var binding: AddPurposeBinding
    private lateinit var adapter: TimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddPurposeBinding.inflate(layoutInflater)
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
                } } }

        binding.save.setOnClickListener{
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        binding.addTime.setOnClickListener{
            //화면에 timePicker 생성
            TimePickerDialog(this, time, 12, 0,true).show()
        }

        adapter = TimeAdapter()
        binding.timeRecycler.layoutManager = LinearLayoutManager(this)
        binding.timeRecycler.adapter = adapter
    }

    private val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        Log.d("listener", "${calendar}")
        datetext = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)}월 ${calendar.get(Calendar.DATE)}일까지"
        Log.d("listener", "${datetext}")

        //선택한 날짜 텍스트로 보여주기
        binding.customText.visibility = View.VISIBLE
        binding.customText.text = datetext
    }

    private val time = TimePickerDialog.OnTimeSetListener{view, hour, minute ->
        val t = listOf(hour, minute)
        Log.d("Listener", "리스너 호출")
        times.add(t)
        Log.d("Listener", "리스트에 추가 ${times}")
        //times의 원소가 1개 이상일 때만 visible로 변경
        binding.timeRecycler.visibility = View.VISIBLE
        //추가된 리스트로 업데이트
        adapter.submitList(times.toMutableList())
        Log.d("Listener", "adapter에 등록")
    }
}