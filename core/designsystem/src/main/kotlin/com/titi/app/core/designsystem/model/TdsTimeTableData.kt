package com.titi.app.core.designsystem.model

import androidx.compose.ui.graphics.Color

data class TdsTimeTableData(
    val hour: Int,
    val start: Long,
    val end: Long,
    val color: Color,
)
