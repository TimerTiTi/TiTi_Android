package com.titi.app.domain.alarm.mapper

import com.titi.app.data.alarm.api.model.AlarmRepositoryModel
import com.titi.app.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.app.domain.alarm.model.Alarm
import com.titi.app.domain.alarm.model.Alarms

internal fun Alarms.toRepositoryModel() = AlarmsRepositoryModel(
    alarms = alarms.map { it.toRepositoryModel() }
)

internal fun Alarm.toRepositoryModel() = AlarmRepositoryModel(
    title = title,
    message = message,
    finishTime = finishTime
)