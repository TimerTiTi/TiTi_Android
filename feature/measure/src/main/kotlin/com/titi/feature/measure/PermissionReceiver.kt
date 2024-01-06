package com.titi.feature.measure

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.titi.core.util.goAsync
import com.titi.domain.alarm.usecase.CanSetAlarmUseCase
import com.titi.domain.alarm.usecase.GetAlarmsUseCase
import com.titi.domain.alarm.usecase.SetAlarmsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class PermissionReceiver : BroadcastReceiver() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PermissionReceiverEntryPoint {
        fun getCanSetAlarmUseCase(): CanSetAlarmUseCase
        fun getGetAlarmsUseCase(): GetAlarmsUseCase
        fun getSetAlarmsUseCase(): SetAlarmsUseCase
    }

    lateinit var canSetAlarmUseCase: CanSetAlarmUseCase
    lateinit var getAlarmsUseCase: GetAlarmsUseCase
    lateinit var setAlarmsUseCase: SetAlarmsUseCase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            PermissionReceiverEntryPoint::class.java
        )
        canSetAlarmUseCase = entryPoint.getCanSetAlarmUseCase()
        getAlarmsUseCase = entryPoint.getGetAlarmsUseCase()
        setAlarmsUseCase = entryPoint.getSetAlarmsUseCase()

        when (intent.action) {
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                if (canSetAlarmUseCase()) {
                    goAsync(GlobalScope, Dispatchers.IO) {
                        val alarms = getAlarmsUseCase()
                        if (alarms != null) {
                            setAlarmsUseCase(alarms)
                        }
                    }
                }
            }
        }
    }

}
