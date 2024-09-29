package com.titi.app.tds.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed interface TtdsButtonInfo {
    val paddingValues: PaddingValues
    val shape: Shape
    val fontSize : TextUnit

    data class Small(
        override val paddingValues: PaddingValues = PaddingValues(12.dp, 8.dp),
        override val shape: Shape = RoundedCornerShape(8.dp),
        override val fontSize: TextUnit = 18.sp,
    ) : TtdsButtonInfo
}