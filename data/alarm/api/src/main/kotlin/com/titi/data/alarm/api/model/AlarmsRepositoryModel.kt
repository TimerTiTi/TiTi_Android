package com.titi.data.alarm.api.model

data class AlarmsRepositoryModel(
    val alarms: List<AlarmRepositoryModel>
)

data class AlarmRepositoryModel(
    val title : String,
    val message: String,
    val finishTime: String,
)