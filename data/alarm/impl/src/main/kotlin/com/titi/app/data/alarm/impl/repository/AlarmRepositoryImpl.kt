package com.titi.app.data.alarm.impl.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.app.data.alarm.impl.AlarmReceiver
import com.titi.app.data.alarm.impl.local.AlarmDataStore
import com.titi.app.data.alarm.impl.mapper.toLocalModel
import com.titi.app.data.alarm.impl.mapper.toRepositoryModel
import org.threeten.bp.ZonedDateTime

internal class AlarmRepositoryImpl(
    private val context: Context,
    private val alarmDataStore: AlarmDataStore
) : AlarmRepository {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun getAlarms(): AlarmsRepositoryModel? =
        alarmDataStore.getAlarms()?.toRepositoryModel()

    override fun canScheduleExactAlarms(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

    override suspend fun setExactAlarms(alarms: AlarmsRepositoryModel) {
        alarms.alarms.forEachIndexed { index, alarm ->
            val pendingIntent = Intent(context, AlarmReceiver::class.java).run {
                putExtra("ALARM_TITLE", alarm.title)
                putExtra("ALARM_MESSAGE", alarm.message)
                PendingIntent.getBroadcast(context, index, this, PendingIntent.FLAG_IMMUTABLE)
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                ZonedDateTime.parse(alarm.finishTime).toInstant().toEpochMilli(),
                pendingIntent
            )
        }

        alarmDataStore.setAlarms(alarms.toLocalModel())
    }

    override suspend fun addExactAlarms(alarms: AlarmsRepositoryModel) {
        alarmDataStore.setAlarms(alarms.toLocalModel())
    }

    override suspend fun cancelAlarms() {
        runCatching {
            alarmDataStore.getAlarms()?.let {
                it.alarms.forEachIndexed { index, _ ->
                    val pendingIntent = Intent(context, AlarmReceiver::class.java).run {
                        PendingIntent.getBroadcast(
                            context,
                            index,
                            this,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    }

                    alarmManager.cancel(pendingIntent)
                }
            }
        }.onSuccess {
            alarmDataStore.removeAlarms()
        }
    }
}