package com.titi.app.core.designsystem.component.piechart

import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

@Immutable
data class TdsPieData(
    val progress : Float,
    val color : Color
)
