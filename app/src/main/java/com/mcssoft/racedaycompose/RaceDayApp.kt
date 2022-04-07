package com.mcssoft.racedaycompose

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RaceDayApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            this.resources.getString(R.string.download_channel_id),
            this.resources.getString(R.string.download_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

}