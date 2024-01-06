package com.titi.domain.alarm.model

data class Alarms(
    val alarms: List<Alarm>
)

data class Alarm(
    val title: String,
    val message: String,
    val finishTime: String,
)