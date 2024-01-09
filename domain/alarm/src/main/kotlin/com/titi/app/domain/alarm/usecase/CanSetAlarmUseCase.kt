package com.titi.app.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import javax.inject.Inject

class CanSetAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke() = alarmRepository.canScheduleExactAlarms()

}