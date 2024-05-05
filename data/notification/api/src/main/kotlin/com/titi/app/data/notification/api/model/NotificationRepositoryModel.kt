package com.titi.app.data.notification.api.model

data class NotificationRepositoryModel(
    val timerFiveMinutesBeforeTheEnd: Boolean = true,
    val timerBeforeTheEnd: Boolean = true,
    val stopwatch: Boolean = true,
)
