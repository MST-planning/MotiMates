package com.example.motimates

import java.util.Calendar

class Purpose(title: String, description:String, times:List<List<Int>>, period:Calendar) {
    private val title:String = title          //목표 이름
    private val description:String = description   //설명
    private val times:List<List<Int>> = times //알림 시간
    private var period:Calendar = period       //종료 일자

    companion object {
        lateinit var purposeList:MutableList<Purpose>

        fun commit(me: Purpose){
            purposeList.add(me)
        }
    }
}