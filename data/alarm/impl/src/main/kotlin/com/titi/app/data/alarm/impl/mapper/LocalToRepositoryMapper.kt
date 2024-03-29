package com.titi.app.data.alarm.impl.mapper

import com.titi.app.data.alarm.api.model.AlarmRepositoryModel
import com.titi.app.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.app.data.alarm.impl.local.model.AlarmEntity
import com.titi.app.data.alarm.impl.local.model.AlarmsEntity

internal fun AlarmsEntity.toRepositoryModel() = AlarmsRepositoryModel(
    alarms = alarms.map { it.toRepositoryModel() },
)

internal fun AlarmEntity.toRepositoryModel() = AlarmRepositoryModel(
    title = title,
    message = message,
    finishTime = finishTime,
)
