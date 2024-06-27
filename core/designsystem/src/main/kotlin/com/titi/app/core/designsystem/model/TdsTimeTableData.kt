package com.titi.app.core.designsystem.model

import androidx.compose.runtime.Immutable

@Immutable
data class TdsTimeTableData(
    val taskName: String,
    val hour: Int,
    val start: Int,
    val end: Int,
)
