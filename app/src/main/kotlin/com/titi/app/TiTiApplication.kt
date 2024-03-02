package com.titi.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.airbnb.mvrx.Mavericks
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TiTiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Mavericks.initialize(this)
        AndroidThreeTen.init(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelId = "titiChannelId"
        val channelName = "TiTi Notifications"
        val channelImportance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(channelId, channelName, channelImportance).apply {
            setShowBadge(false)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
