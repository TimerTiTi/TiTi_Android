package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeLineDailyGraph(
    todayDate: String,
    todayDayOfTheWeek: Int,
    tdsColors: List<TdsColor>,
    timeLines: List<Int>,
    totalTime: String,
    maxTime: String,
) {
    Box(
        modifier = Modifier.size(365.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .size(345.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 10.dp),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                TdsText(
                    text = todayDate,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = 25.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(5.dp))

                TdsDayOfTheWeek(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    color = tdsColors.first(),
                )

                Spacer(modifier = Modifier.height(15.dp))

                TdsText(
                    text = "TimeLine",
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(2.dp))

                TdsTimeLineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
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
                            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                            fontSize = 12.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = totalTime,
                            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                            fontSize = 22.sp,
                            color = tdsColors.first(),
                        )
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TdsText(
                            text = "Max",
                            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                            fontSize = 12.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = maxTime,
                            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                            fontSize = 22.sp,
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
        )
    }
}
