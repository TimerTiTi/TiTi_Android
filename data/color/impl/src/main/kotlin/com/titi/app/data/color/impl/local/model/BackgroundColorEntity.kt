package com.titi.app.data.color.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BackgroundColorEntity(
    val backgroundColors: List<Long>
)
