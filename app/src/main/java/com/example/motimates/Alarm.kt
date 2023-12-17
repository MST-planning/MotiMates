package com.example.motimates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Alarm:BroadcastReceiver() {

    private val notice = NotificationPush()
    override fun onReceive(context: Context?, intent: Intent?) {
        notice.noticefy()
    }
}