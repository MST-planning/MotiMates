package com.example.motimates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {
    private val NONCODE = 999;
    private lateinit var t:String

    lateinit var text:String

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("alarm", "onReceive 호출")

        //알람에서 넘긴 코드로 해당 목표 찾기
        val pCode = intent!!.getIntExtra("code", NONCODE)
        val purpose = Purpose.find(pCode)

        //목표가 존재하면 세팅 후 알람
        if (purpose != null){
            text = "이제 ${purpose.getTitle()} 할 시간이예요~"
            MainActivity.getMain().notify(text)
        }else Log.d("alarm", "알람 오류. 알람이 호출되었으나 해당 목표를 찾을 수 없습니다.")
    }
}