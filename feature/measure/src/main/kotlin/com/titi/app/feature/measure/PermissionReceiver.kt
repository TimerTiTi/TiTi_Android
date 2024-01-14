package com.titi.app.feature.measure

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.titi.app.core.util.goAsync
import com.titi.app.domain.alarm.usecase.CanSetAlarmUseCase
import com.titi.app.domain.alarm.usecase.GetAlarmsUseCase
import com.titi.app.domain.alarm.usecase.SetAlarmsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal class PermissionReceiver : BroadcastReceiver() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface ReceiverEntryPoint {
        fun getCanSetAlarmUseCase(): CanSetAlarmUseCase

        fun getGetAlarmsUseCase(): GetAlarmsUseCase

        fun getSetAlarmsUseCase(): SetAlarmsUseCase
    }

    lateinit var canSetAlarmUseCase: CanSetAlarmUseCase
    lateinit var getAlarmsUseCase: GetAlarmsUseCase
    lateinit var setAlarmsUseCase: SetAlarmsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val entryPoint =
            EntryPointAccessors.fromApplication(
                context,
                ReceiverEntryPoint::class.java
            )
        canSetAlarmUseCase = entryPoint.getCanSetAlarmUseCase()
        getAlarmsUseCase = entryPoint.getGetAlarmsUseCase()
        setAlarmsUseCase = entryPoint.getSetAlarmsUseCase()

        if (intent.action == AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED) {
            if (canSetAlarmUseCase()) {
                goAsync(CoroutineScope(Dispatchers.IO)) {
                    val alarms = getAlarmsUseCase()
                    if (alarms != null) {
                        setAlarmsUseCase(alarms)
                    }
                }
            }
        }
    }
}
