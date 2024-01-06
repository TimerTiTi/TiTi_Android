package com.titi.domain.alarm.usecase

import com.titi.data.alarm.api.AlarmRepository
import com.titi.domain.alarm.mapper.toDomainModel
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke() = alarmRepository.getAlarms()?.toDomainModel()

}