package com.example.motimates

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "testdb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table TodayGoalList (" +
                "goal text not null," +
                "date text not null," +
                "image blob)")
    } //todaygoalllist: 오늘의 목표와 날짜, 사진 정보를 저장

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}