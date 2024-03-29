package com.titi.app.domain.alarm.mapper

import com.titi.app.data.alarm.api.model.AlarmRepositoryModel
import com.titi.app.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.app.domain.alarm.model.Alarm
import com.titi.app.domain.alarm.model.Alarms

internal fun AlarmsRepositoryModel.toDomainModel() = Alarms(
    alarms = alarms.map { it.toDomainModel() },
)

internal fun AlarmRepositoryModel.toDomainModel() = Alarm(
    title = title,
    message = message,
    finishTime = finishTime,
)
