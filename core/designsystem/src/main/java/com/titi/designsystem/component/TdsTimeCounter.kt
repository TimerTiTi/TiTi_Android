package com.titi.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeCounter(
    modifier: Modifier = Modifier,
    hour: Int,
    minutes: Int,
    seconds: Int,
) {
    Row(modifier = modifier) {
        TdsAnimatedCounter(count = hour)
        Text(text = ":")
        TdsAnimatedCounter(count = minutes)
        Text(text = ":")
        TdsAnimatedCounter(count = seconds)
    }
}

@Preview
@Composable
private fun TdsTimeCounterPreview() {
    TiTiTheme {
        TdsTimeCounter(
            hour = 1,
            minutes = 10,
            seconds = 55,
        )
    }
}