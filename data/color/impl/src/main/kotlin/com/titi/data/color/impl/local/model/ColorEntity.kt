package com.titi.data.color.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ColorEntity(
    val timerBackgroundColor: Long,
    val timerTextColor: Long,
    val stopwatchBackgroundColor: Long,
    val stopwatchTextColor: Long,
)
