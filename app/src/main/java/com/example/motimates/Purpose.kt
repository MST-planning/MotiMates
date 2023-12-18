package com.example.motimates

import android.content.Context
import android.content.Intent
import com.google.android.play.core.integrity.p
import java.util.Calendar

class Purpose(title: String, description:String, times:List<List<Int>>, period:Calendar) {
    private val title:String = title          //목표 이름
    private val description:String = description   //설명
    private val times:List<List<Int>> = times //알림 시간
    private var period:Calendar = period       //종료 일자
    private val code = count

    fun getCode():Int = code
    fun getTitle():String = title
    fun getTimes():List<List<Int>> = times

    companion object {
        var purposeList = mutableListOf<Purpose>()
        var count = 0

        fun commit(me: Purpose){
            purposeList.add(me)
            count++
        }
        fun find(code: Int):Purpose?
        {
            for (p in purposeList)
                if (p.getCode() == code)
                    return p

            return null
        }
    }
}