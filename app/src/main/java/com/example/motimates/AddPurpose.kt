package com.example.motimates

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
    //객체로 세팅
    private lateinit var purpose: Purpose
    //기능을 위한 객체
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
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
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
        purpose = Purpose(binding.title.text.toString(), binding.content.text.toString(), adapter.currentList.toList(), calendar)
        Purpose.commit(purpose)

        var i = 0;

        for (time in purpose.getTimes())
        {
            var cal = Calendar.getInstance()

            cal.set(Calendar.HOUR_OF_DAY, time[0])
            cal.set(Calendar.MINUTE, time[1])
            cal.set(Calendar.SECOND, 0)
            if (cal.before(Calendar.getInstance()))
                cal.add(Calendar.DATE, 1)

            setAlarm(cal, i)
            i++
        }
    }

    private fun setAlarm(cal: Calendar, num: Int)
    {
        Log.d("alarm", "setAlarm 호출")
        val alarmNum = purpose.getCode() * 100 + num

        val manager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("code", purpose.getCode())
        val pIntent = PendingIntent.getBroadcast(this, alarmNum, intent, PendingIntent.FLAG_MUTABLE)
        Log.d("alarm", "${cal.timeInMillis}m ${cal.toString()}")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && manager.canScheduleExactAlarms())
        {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pIntent)
            Log.d("alarm", "매니저한테 부탁도 했고")
        }
        else Toast.makeText(this, "앱 설정 > 권한 탭에서 스케쥴 권한을 허용해주세요", Toast.LENGTH_LONG).show()


        Log.d("alarm", "setAlarm 종료")
    }

    fun delAlarm(p:Purpose)
    {
        val code = p.getCode()

        val manager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        for(i in 1 until p.getTimes().count())
        {
            val c = code * 100 + i
            val pendingIntent = PendingIntent.getBroadcast(this, c, intent, PendingIntent.FLAG_IMMUTABLE)

            manager.cancel(pendingIntent)
        }
    }
}