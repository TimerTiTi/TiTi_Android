package com.titi.app.core.designsystem.model

import androidx.compose.runtime.Immutable

@Immutable
data class TdsTaskData(
    val key: String,
    val value: String,
    val progress: Float,
)
