package com.titi.data.alarm.api.model

data class AlarmsRepositoryModel(
    val alarms: List<AlarmRepositoryModel>
)

data class AlarmRepositoryModel(
    val message: String,
    val delayMillis: Long,
)