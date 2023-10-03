package com.titi.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.titi.designsystem.theme.TiTiTheme

data class TdsTime(
    val hour: Int,
    val minutes: Int,
    val seconds: Int,
)

@Composable
fun TdsTimeCounter(
    modifier: Modifier = Modifier,
    tdsTime: TdsTime,
    color: Color,
    textStyle: TextStyle
) {
    Row(modifier = modifier) {
        TdsAnimatedCounter(
            count = tdsTime.hour,
            color = color,
            textStyle = textStyle
        )
        TdsText(
            text = ":",
            color = color,
            textStyle = textStyle
        )
        TdsAnimatedCounter(
            count = tdsTime.minutes,
            color = color,
            textStyle = textStyle
        )
        TdsText(
            text = ":",
            color = color,
            textStyle = textStyle
        )
        TdsAnimatedCounter(
            count = tdsTime.seconds,
            color = color,
            textStyle = textStyle
        )
    }
}

@Preview
@Composable
private fun TdsTimeCounterPreview() {
    TiTiTheme {
        TdsTimeCounter(
            tdsTime = TdsTime(
                hour = 20,
                minutes = 20,
                seconds = 20,
            ),
            color = TiTiTheme.colors.blackColor,
            textStyle = TiTiTheme.textStyle.normalTextStyle.copy(fontSize = 40.sp)
        )
    }
}