package com.titi.app.core.designsystem.model

import androidx.compose.runtime.Immutable

@Immutable
data class TdsTimeTableData(
    val hour: Int,
    val start: Long,
    val end: Long,
)
