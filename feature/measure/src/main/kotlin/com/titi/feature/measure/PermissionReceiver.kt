package com.titi.feature.measure

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.titi.domain.alarm.usecase.CanSetAlarmUseCase
import com.titi.domain.alarm.usecase.GetAlarmsUseCase
import com.titi.domain.alarm.usecase.SetAlarmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PermissionReceiver @Inject constructor(
    private val canSetAlarmUseCase: CanSetAlarmUseCase,
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val setAlarmsUseCase: SetAlarmsUseCase
) : BroadcastReceiver() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                if(canSetAlarmUseCase()){
                    goAsync(GlobalScope, Dispatchers.IO){
                        val alarms = getAlarmsUseCase()
                        if(alarms != null){
                            setAlarmsUseCase(alarms)
                        }
                    }
                }
            }
        }
    }

    private fun BroadcastReceiver.goAsync(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        block: suspend () -> Unit
    ) {
        val pendingResult = goAsync()
        coroutineScope.launch(dispatcher) {
            block()
            pendingResult.finish()
        }
    }

}