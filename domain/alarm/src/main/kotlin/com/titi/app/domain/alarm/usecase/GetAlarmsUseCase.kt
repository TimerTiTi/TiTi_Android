package com.titi.app.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.domain.alarm.mapper.toDomainModel
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke() = alarmRepository.getAlarms()?.toDomainModel()
}
