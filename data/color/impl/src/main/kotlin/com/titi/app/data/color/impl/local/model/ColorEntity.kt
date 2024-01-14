package com.titi.app.data.color.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ColorEntity(
    val timerBackgroundColor: Long,
    val isTimerBlackTextColor: Boolean,
    val stopwatchBackgroundColor: Long,
    val isStopwatchBlackTextColor: Boolean
)
