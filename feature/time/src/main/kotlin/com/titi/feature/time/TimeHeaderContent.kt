package com.titi.feature.time

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.core.designsystem.component.TdsIconButton
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.designsystem.R

@Composable
fun TimeHeaderContent(
    todayDate: String,
    isDailyAfter6AM: Boolean,
    textColor: TdsColor,
    onClickColor: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TdsText(
            modifier = Modifier.align(Alignment.Center),
            text = todayDate,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 16.sp,
            color = if (isDailyAfter6AM) textColor else TdsColor.redColor
        )

        TdsIconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onClickColor,
            size = 32.dp
        ) {
            Icon(
                painter = painterResource(id = R.drawable.color_selector_icon),
                contentDescription = "setColorIcon",
                tint = Color.Unspecified
            )
        }

    }
}