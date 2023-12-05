package com.example.motimates

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat

class NotificationPush : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun noticefy()
    {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //채널 정보 변수
            val channelId = "stateBar"
            val channelName = "상태바 알림"
            val channelDescription = "정한 시간대에 상태바로 알림 주는 겁니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            //NotificationChannel 객체 생성
            val channelState = NotificationChannel(channelId, channelName, importance)
            channelState.description = channelDescription

            //notification Manager에 채널 등록
            notificationManager.createNotificationChannel(channelState)

            builder = NotificationCompat.Builder(this, channelId)
        }
        else{
            builder = NotificationCompat.Builder(this)
        }

        //알림 구성
        builder.run {
            setSmallIcon(R.drawable.logo)
            setContentTitle(R.string.Title.toString())
            setContentText(R.string.content.toString())
            setAutoCancel(true)
        }

        //매니저에 알림 등록
        notificationManager.notify(0, builder.build())
    }
}