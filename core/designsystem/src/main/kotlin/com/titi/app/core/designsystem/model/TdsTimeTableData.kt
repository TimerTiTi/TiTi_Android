package com.titi.app.core.designsystem.model

import androidx.compose.runtime.Immutable
import com.titi.app.core.designsystem.theme.TdsColor

@Immutable
data class TdsTimeTableData(
    val color: TdsColor,
    val hour: Int,
    val start: Int,
    val end: Int,
)
