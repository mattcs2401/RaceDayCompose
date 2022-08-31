package com.mcssoft.racedaycompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
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
        // TODO - integrate the notifications.
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(channel)
    }

}