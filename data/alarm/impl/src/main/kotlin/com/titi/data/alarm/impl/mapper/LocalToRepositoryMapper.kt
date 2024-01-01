package com.titi.data.alarm.impl.mapper

import com.titi.data.alarm.api.model.AlarmRepositoryModel
import com.titi.data.alarm.api.model.AlarmsRepositoryModel
import com.titi.data.alarm.impl.local.model.AlarmEntity
import com.titi.data.alarm.impl.local.model.AlarmsEntity

internal fun AlarmsEntity.toRepositoryModel() = AlarmsRepositoryModel(
    alarms = alarms.map { it.toRepositoryModel() }
)

internal fun AlarmEntity.toRepositoryModel() = AlarmRepositoryModel(
    message = message,
    delayMillis = delayMillis
)