package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.model.TdsTime
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeCounter(
    modifier: Modifier = Modifier,
    tdsTime: TdsTime,
    color: Color,
    textStyle: TdsTextStyle,
    fontSize: TextUnit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        TdsAnimatedCounter(
            modifier = Modifier.weight(1f),
            count = tdsTime.hour,
            color = color,
            textStyle = textStyle,
            fontSize = fontSize,
        )
        TdsText(
            text = ":",
            color = color,
            textStyle = textStyle,
            fontSize = fontSize,
        )
        TdsAnimatedCounter(
            modifier = Modifier.weight(1f),
            count = tdsTime.minutes,
            color = color,
            textStyle = textStyle,
            fontSize = fontSize,
        )
        TdsText(
            text = ":",
            color = color,
            textStyle = textStyle,
            fontSize = fontSize,
        )
        TdsAnimatedCounter(
            modifier = Modifier.weight(1f),
            count = tdsTime.seconds,
            color = color,
            textStyle = textStyle,
            fontSize = fontSize,
        )
    }
}

@Preview
@Composable
private fun TdsTimeCounterPreview() {
    TiTiTheme {
        TdsTimeCounter(
            tdsTime =
            TdsTime(
                hour = 20,
                minutes = 20,
                seconds = 20,
            ),
            color = TdsColor.TEXT.getColor(),
            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
            fontSize = 40.sp,
        )
    }
}
