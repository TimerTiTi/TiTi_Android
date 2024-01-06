package com.titi.domain.alarm.usecase

import com.titi.data.alarm.api.AlarmRepository
import javax.inject.Inject

class CancelAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke() {
        alarmRepository.cancelAlarms()
    }

}