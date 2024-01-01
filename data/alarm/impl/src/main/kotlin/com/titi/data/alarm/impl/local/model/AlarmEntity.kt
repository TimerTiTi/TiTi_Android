package com.titi.data.alarm.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class AlarmsEntity(
    val alarms: List<AlarmEntity>
)

@JsonClass(generateAdapter = true)
internal data class AlarmEntity(
    val message: String,
    val delayMillis: Long,
)
