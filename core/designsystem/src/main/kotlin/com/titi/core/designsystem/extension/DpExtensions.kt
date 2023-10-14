package com.titi.core.designsystem.extension

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

operator fun Dp.times(d: Double): Dp {
    return (this.value * d).dp
}