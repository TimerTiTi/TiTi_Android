package com.titi.data.alarm.impl

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("ALARM_TITLE") ?: return
        val message = intent.getStringExtra("ALARM_MESSAGE")
        val channelId = "titiChannelId"

        context?.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notificationManager.notify(0, builder.build())
        }
    }

}