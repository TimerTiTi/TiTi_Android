package com.titi.app.core.designsystem.component

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.getMaxTime
import com.titi.app.core.designsystem.util.getSumTime

@Composable
fun TdsTimeTableDailyGraph(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    tdsColors: List<TdsColor>,
    taskData: List<TdsTaskData>,
    timeTableData: List<TdsTimeTableData>,
) {
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
                        text = todayDate,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 25.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    TdsDayOfTheWeek(
                        modifier = Modifier.padding(bottom = 2.dp),
                        todayDayOfTheWeek = todayDayOfTheWeek,
                        color = tdsColors.first(),
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .width(size * 0.6)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TdsText(
                            text = "Times",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = 16.sp,
                            color = TdsColor.TEXT,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .border(
                                    width = 2.dp,
                                    color = TdsColor.GRAPH_BORDER.getColor(),
                                )
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TdsPieChart(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 10.dp),
                                taskData = taskData,
                                colors = tdsColors.map { it.getColor() },
                            )

                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(end = 24.dp),
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

                                TdsText(
                                    text = "Max",
                                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                                    fontSize = 12.sp,
                                    color = TdsColor.TEXT,
                                )

                                TdsText(
                                    text = taskData.getMaxTime(),
                                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                                    fontSize = 22.sp,
                                    color = tdsColors.first(),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        TdsTaskResultList(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = TdsColor.GRAPH_BORDER.getColor(),
                                )
                                .padding(2.dp)
                                .padding(horizontal = 6.dp),
                            taskData = taskData,
                            colors = tdsColors.map { it.getColor() },
                            isSpacing = true,
                            leftText = "✔",
                            height = 20.dp,
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TdsText(
                            text = "TimeTable",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = 16.sp,
                            color = TdsColor.TEXT,
                        )

                        TdsTimeTable(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = TdsColor.GRAPH_BORDER.getColor(),
                                )
                                .padding(2.dp),
                            timeTableData = timeTableData,
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
private fun TdsTimeTableDailyGraphPreview() {
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
        TdsTimeTableDailyGraph(
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
            taskData = taskData,
            timeTableData = listOf(
                TdsTimeTableData(
                    hour = 3,
                    start = 1800,
                    end = 2400,
                ),
                TdsTimeTableData(
                    hour = 5,
                    start = 1234,
                    end = 2555,
                ),
                TdsTimeTableData(
                    hour = 12,
                    start = 600,
                    end = 3444,
                ),
                TdsTimeTableData(
                    hour = 23,
                    start = 2121,
                    end = 3333,
                ),
            ),
        )
    }
}
