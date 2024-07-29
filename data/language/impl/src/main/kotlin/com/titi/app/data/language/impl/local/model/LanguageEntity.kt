package com.titi.app.data.language.impl.local.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LanguageEntity(
    val system: Boolean,
    val korean: Boolean,
    val english: Boolean,
    val china: Boolean,
)
