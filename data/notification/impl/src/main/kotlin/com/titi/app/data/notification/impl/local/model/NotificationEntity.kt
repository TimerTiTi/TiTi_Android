package com.titi.app.data.notification.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class NotificationEntity(
    val timerFiveMinutesBeforeTheEnd: Boolean,
    val timerBeforeTheEnd: Boolean,
    val stopwatch: Boolean,
)
