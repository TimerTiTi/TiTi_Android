package com.titi.app.data.notification.api.model

data class NotificationRepositoryModel(
    val timerFiveMinutesBeforeTheEnd: Boolean,
    val timerBeforeTheEnd: Boolean,
    val stopwatch: Boolean,
)
