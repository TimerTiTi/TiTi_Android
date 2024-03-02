package com.titi.app.data.alarm.impl

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.titi.app.core.util.goAsync
import com.titi.app.data.alarm.impl.local.AlarmDataStore
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal class AlarmReceiver : BroadcastReceiver() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface AlarmReceiverEntryPoint {
        fun getAlarmDataStore(): AlarmDataStore
    }

    private lateinit var alarmDataStore: AlarmDataStore

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("ALARM_TITLE") ?: return
        val message = intent.getStringExtra("ALARM_MESSAGE")
        val channelId = "titiChannelId"
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            AlarmReceiverEntryPoint::class.java,
        )
        alarmDataStore = entryPoint.getAlarmDataStore()

        val deepLink = "titi://"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(0, builder.build())

        goAsync(CoroutineScope(Dispatchers.IO)) {
            alarmDataStore.removeAlarms()
        }
    }
}
