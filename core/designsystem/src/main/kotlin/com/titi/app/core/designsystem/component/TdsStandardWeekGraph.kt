package com.titi.app.core.designsystem.component

import android.graphics.Picture
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.createCaptureImageModifier

@Composable
fun TdsStandardWeekGraph(
    modifier: Modifier = Modifier,
    weekInformation: Triple<String, String, String>,
    totalTime: String,
    averageTime: String,
    weekLineChartData: List<TdsWeekLineChartData>,
    tdsColors: List<TdsColor>,
    topLevelTaskData: List<TdsTaskData>,
    topLevelTaskTotal: String,
    picture: Picture,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val size = maxWidth.coerceAtMost(345.dp)

        OutlinedCard(
            modifier = Modifier
                .createCaptureImageModifier(picture = picture)
                .size(size),
            shape = RoundedCornerShape(size * 0.07),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            border = BorderStroke(
                width = 3.dp,
                TdsColor.SHADOW.getColor(),
            ),
        ) {
            Column(
                modifier = Modifier.padding(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    TdsText(
                        text = weekInformation.first,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = (size.value * 0.07).sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    TdsText(
                        text = weekInformation.second,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = (size.value * 0.07).sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TdsText(
                        text = weekInformation.third,
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = (size.value * 0.05).sp,
                        color = TdsColor.TEXT,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(size * 0.4)
                        .border(
                            width = 2.dp,
                            color = TdsColor.GRAPH_BORDER.getColor(),
                        )
                        .padding(2.dp),
                ) {
                    TdsWeekLineChart(
                        modifier = Modifier
                            .width(size * 0.6)
                            .fillMaxHeight()
                            .padding(horizontal = 4.dp),
                        weekLineChartData = weekLineChartData,
                        startColor = tdsColors.first().getColor(),
                        endColor = tdsColors[2].getColor(),
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        TdsText(
                            text = "Total",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.04).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = totalTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = tdsColors.first(),
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        TdsText(
                            text = "Average",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.04).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = averageTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = tdsColors.first(),
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    TdsTaskResultList(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(size * 0.6)
                            .border(
                                width = 2.dp,
                                color = TdsColor.GRAPH_BORDER.getColor(),
                            )
                            .padding(2.dp)
                            .padding(horizontal = 6.dp),
                        taskData = topLevelTaskData,
                        leftText = "Top",
                        height = 25.dp,
                        colors = tdsColors.map { it.getColor() },
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 2.dp,
                                color = TdsColor.GRAPH_BORDER.getColor(),
                            )
                            .padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        TdsText(
                            text = "Top 5",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.04).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = topLevelTaskTotal,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = tdsColors.first(),
                        )

                        TdsPieChart(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 3.dp),
                            taskData = topLevelTaskData,
                            colors = tdsColors.map { it.getColor() },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TdsStandardWeekGraphPreview() {
    val taskData = listOf(
        TdsTaskData(
            key = "수업",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "인공지능",
            value = "03:00:00",
            progress = 0.3f,
        ),
        TdsTaskData(
            key = "알고리즘",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "개발",
            value = "03:00:00",
            progress = 0.3f,
        ),
    )

    TiTiTheme {
        TdsStandardWeekGraph(
            modifier = Modifier.fillMaxWidth(),
            totalTime = "08:00:00",
            averageTime = "03:00:00",
            weekInformation = Triple("2024.02", "Week 2", "02.12~02.19"),
            weekLineChartData = listOf(
                TdsWeekLineChartData(
                    time = 6200,
                    date = "1/12",
                ),
                TdsWeekLineChartData(
                    time = 3700,
                    date = "1/13",
                ),
                TdsWeekLineChartData(
                    time = 5200,
                    date = "1/14",
                ),
                TdsWeekLineChartData(
                    time = 1042,
                    date = "1/15",
                ),
                TdsWeekLineChartData(
                    time = 4536,
                    date = "1/16",
                ),
                TdsWeekLineChartData(
                    time = 3700,
                    date = "1/17",
                ),
                TdsWeekLineChartData(
                    time = 2455,
                    date = "1/18",
                ),
            ),
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
            ),
            topLevelTaskData = taskData,
            topLevelTaskTotal = "08:00:00",
            picture = Picture(),
        )
    }
}
