package com.titi.app.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeLineDailyGraph(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    totalTime: String,
    maxTime: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier.padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        val size = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        OutlinedCard(
            modifier = Modifier
                .size(size),
            shape = RoundedCornerShape(size * 0.07),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp),
            border = BorderStroke(
                width = 3.dp,
                TdsColor.SHADOW.getColor(),
            ),
        ) {
            Column(
                modifier = Modifier.padding(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    TdsToggleIconButton(
                        modifier = Modifier.padding(2.dp).align(Alignment.CenterStart),
                        checkedIcon = R.drawable.checked_icon,
                        uncheckedIcon = R.drawable.unchecked_icon,
                        checked = checked,
                        onCheckedChange = onCheckedChange,
                    )

                    TdsText(
                        modifier = Modifier.padding(2.dp).align(Alignment.Center),
                        text = todayDate,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = (size.value * 0.07).sp,
                        color = TdsColor.TEXT,
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                TdsDayOfTheWeek(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    color = tdsColors.first(),
                )

                Spacer(modifier = Modifier.height(15.dp))

                TdsText(
                    text = "TimeLine",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = (size.value * 0.05).sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(2.dp))

                TdsTimeLineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(size * 0.4),
                    times = timeLines,
                    startColor = tdsColors[0].getColor(),
                    endColor = tdsColors[1].getColor(),
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TdsText(
                            text = "Total",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = totalTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.1).sp,
                            color = tdsColors.first(),
                        )
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TdsText(
                            text = "Max",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = maxTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.1).sp,
                            color = tdsColors.first(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TdsTimeLineDailyGraphPreview() {
    TiTiTheme {
        TdsTimeLineDailyGraph(
            modifier = Modifier.fillMaxWidth(),
            todayDate = "2024.02.04",
            todayDayOfTheWeek = 6,
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
            ),
            timeLines = listOf(
                3600,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
            ),
            totalTime = "10:00:00",
            maxTime = "03:00:00",
            checked = false,
            onCheckedChange = {},
        )
    }
}
