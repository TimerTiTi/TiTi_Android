package com.titi.app.data.alarm.impl.mapper

import com.titi.app.data.alarm.api.model.AlarmRepositoryModel
import com.titi.app.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.app.data.alarm.impl.local.model.AlarmEntity
import com.titi.app.data.alarm.impl.local.model.AlarmsEntity

internal fun AlarmsRepositoryModel.toLocalModel() = AlarmsEntity(
    alarms = alarms.map { it.toLocalModel() },
)

internal fun AlarmRepositoryModel.toLocalModel() = AlarmEntity(
    title = title,
    message = message,
    finishTime = finishTime,
)
