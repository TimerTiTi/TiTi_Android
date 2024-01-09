package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor

@Composable
fun RowScope.TdsDivider(
    thickness: Dp = 1.dp,
    color: TdsColor = TdsColor.dividerColor,
) {
    Divider(
        modifier = Modifier
            .width(thickness)
            .fillMaxHeight(),
        thickness = thickness,
        color = color.getColor()
    )
}

@Composable
fun ColumnScope.TdsDivider(
    thickness: Dp = 1.dp,
    color: TdsColor = TdsColor.dividerColor,
) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness),
        thickness = thickness,
        color = color.getColor()
    )
}