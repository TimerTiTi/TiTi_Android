package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsDayOfTheWeek(modifier: Modifier = Modifier, todayDayOfTheWeek: Int, color: TdsColor) {
    val dayOfTheWeeks = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(7) { idx ->
            val backgroundModifier = if (idx == todayDayOfTheWeek) {
                Modifier.background(color = color.getColor())
            } else {
                Modifier
            }

            TdsText(
                modifier = Modifier.width(25.dp).height(15.dp).then(backgroundModifier),
                text = dayOfTheWeeks[idx],
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 13.sp,
                color = TdsColor.TEXT,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun TdsDayOfTheWeekPreview() {
    TiTiTheme {
        TdsDayOfTheWeek(
            modifier = Modifier.fillMaxWidth(),
            todayDayOfTheWeek = 3,
            color = TdsColor.D1,
        )
    }
}
