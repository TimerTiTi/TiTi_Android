package com.titi.feature.measure

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.titi.core.util.goAsync
import com.titi.domain.alarm.usecase.CanSetAlarmUseCase
import com.titi.domain.alarm.usecase.GetAlarmsUseCase
import com.titi.domain.alarm.usecase.SetAlarmsUseCase
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BootReceiver : BroadcastReceiver() {

    lateinit var canSetAlarmUseCase: CanSetAlarmUseCase
    lateinit var getAlarmsUseCase: GetAlarmsUseCase
    lateinit var setAlarmsUseCase: SetAlarmsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            PermissionReceiver.ReceiverEntryPoint::class.java
        )
        canSetAlarmUseCase = entryPoint.getCanSetAlarmUseCase()
        getAlarmsUseCase = entryPoint.getGetAlarmsUseCase()
        setAlarmsUseCase = entryPoint.getSetAlarmsUseCase()

        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
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