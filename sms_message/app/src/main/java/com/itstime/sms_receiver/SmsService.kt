package com.itstime.sms_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class SmsService : Service() {
    private val myChannelId = "ChannelId"
    private val id = 10

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            startForeground(id, createNotification())
            startApp()
        }
    }


    private fun createNotificationChannel() {
        val notificationChannel =
            NotificationChannel(myChannelId, "MyService", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, myChannelId)
            .setContentTitle("Service is running")
            .setContentText("Service is running")
            .build()
    }

    private fun startApp(){
        if (Settings.canDrawOverlays(this)){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}