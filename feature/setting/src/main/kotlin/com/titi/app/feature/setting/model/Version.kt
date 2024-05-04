package com.titi.app.feature.setting.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Version(
    val currentVersion: String = "",
    val date: String = "",
    val description: String = "",
)
