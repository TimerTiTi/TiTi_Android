package com.titi.app.core.designsystem.component

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.getSumTime
import kotlin.math.min
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

@Composable
fun TdsStandardWeekGraph(
    modifier: Modifier = Modifier,
    todayDateTime: ZonedDateTime,
    weekLineChardData: List<TdsWeekLineChartData>,
    tdsColors: List<TdsColor>,
    taskData: List<TdsTaskData>,
) {
    val weekInformation = todayDateTime.getWeekInformation()

    BoxWithConstraints(
        modifier = modifier.padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        val size = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        Card(
            modifier = Modifier
                .size(size),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 10.dp),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    TdsText(
                        text = weekInformation.first,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 25.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    TdsText(
                        text = weekInformation.second,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 25.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TdsText(
                        text = weekInformation.third,
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 14.sp,
                        color = TdsColor.TEXT,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .border(
                            width = 2.dp,
                            color = TdsColor.GRAPH_BORDER.getColor(),
                        )
                        .padding(2.dp),
                ) {
                    TdsWeekLineChart(
                        modifier = Modifier
                            .width(size * 0.6)
                            .fillMaxHeight(),
                        weekLineChardData = weekLineChardData,
                        startColor = tdsColors.first().getColor(),
                        endColor = tdsColors.get(2).getColor(),
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        TdsText(
                            text = "Total",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = 12.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = weekLineChardData.sumOf { it.time }.getTimeString(),
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = 22.sp,
                            color = tdsColors.first(),
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        TdsText(
                            text = "Max",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = 12.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = weekLineChardData.maxOf { it.time }.getTimeString(),
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = 22.sp,
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
                        taskData = taskData,
                        isSpacing = true,
                        leftText = "Top",
                        height = 20.dp,
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
                            .padding(2.dp)
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TdsText(
                            text = "Total",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = 12.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = taskData.getSumTime(),
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = 22.sp,
                            color = tdsColors.first(),
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TdsPieChart(
                            modifier = Modifier.fillMaxSize(),
                            taskData = taskData,
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
            todayDateTime = ZonedDateTime.now(ZoneOffset.UTC),
            weekLineChardData = listOf(
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
            taskData = taskData
                .sortedByDescending { it.progress }
                .subList(0, min(taskData.size, 5)),
        )
    }
}
