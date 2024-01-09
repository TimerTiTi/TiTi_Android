package com.titi.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.domain.alarm.mapper.toRepositoryModel
import com.titi.domain.alarm.model.Alarms
import javax.inject.Inject

class SetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke(alarms: Alarms) {
        alarmRepository.setExactAlarms(alarms.toRepositoryModel())
    }

}