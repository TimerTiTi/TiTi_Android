package com.titi.data.alarm.impl.mapper

import com.titi.data.alarm.api.model.AlarmRepositoryModel
import com.titi.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.data.alarm.impl.local.model.AlarmEntity
import com.titi.data.alarm.impl.local.model.AlarmsEntity

internal fun AlarmsRepositoryModel.toLocalModel() = AlarmsEntity(
    alarms = alarms.map { it.toLocalModel() }
)

internal fun AlarmRepositoryModel.toLocalModel() = AlarmEntity(
    title = title,
    message = message,
    finishTime = finishTime
)