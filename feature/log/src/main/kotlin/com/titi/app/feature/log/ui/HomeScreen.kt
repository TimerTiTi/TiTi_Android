package com.titi.app.feature.log.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.component.TdsCard
import com.titi.app.core.designsystem.component.TdsCircularProgressIndicator
import com.titi.app.core.designsystem.component.TdsDayOfTheWeek
import com.titi.app.core.designsystem.component.TdsFilledCard
import com.titi.app.core.designsystem.component.TdsPieChart
import com.titi.app.core.designsystem.component.TdsTaskResultList
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimeLineChart
import com.titi.app.core.designsystem.component.TdsWeekLineChart
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.model.GraphGoalTimeUiState
import com.titi.app.feature.log.model.HomeUiState

@Composable
fun HomeScreen(
    tdsColors: List<TdsColor>,
    totalData: HomeUiState.TotalData,
    graphGoalTimeUiState: GraphGoalTimeUiState,
    homeMonthGraphData: HomeUiState.HomeMonthGraphData,
    homeWeekGraphData: HomeUiState.HomeWeekGraphData,
    homeDailyGraphData: HomeUiState.HomeDailyGraphData,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TotalCard(
            totalData = totalData,
            tdsColors = tdsColors,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Total",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(339.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MonthSumCard(
                totalTimeSeconds = homeMonthGraphData.totalTimeSeconds,
                goalTimeSeconds = graphGoalTimeUiState.monthGoalTime * 3600L,
                themeColor = tdsColors.first(),
            )

            WeekSumCard(
                totalTimeSeconds = homeWeekGraphData.totalTimeSeconds,
                goalTimeSeconds = graphGoalTimeUiState.weekGoalTime * 3600L,
                themeColor = tdsColors.first(),
            )
        }

        MonthCard(
            homeMonthGraphData = homeMonthGraphData,
            tdsColors = tdsColors,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Month",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 11.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        WeekCard(
            homeWeekGraphData = homeWeekGraphData,
            tdsColors = tdsColors,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Week",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 11.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TimeLineCard(
            homeDailyGraphData = homeDailyGraphData,
            tdsColors = tdsColors,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Daily",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 11.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun TotalCard(totalData: HomeUiState.TotalData, tdsColors: List<TdsColor>) {
    TdsFilledCard(
        modifier = Modifier
            .width(339.dp)
            .height(158.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TdsPieChart(
                modifier = Modifier.size(109.dp),
                taskData = totalData.topTotalTdsTaskData,
                colors = tdsColors.map { it.getColor() },
                totalTimeString = (totalData.totalTimeSeconds / 3600).toString(),
            )

            Spacer(modifier = Modifier.width(10.dp))

            TdsTaskResultList(
                modifier = Modifier.weight(1f),
                taskData = totalData.topTotalTdsTaskData,
                colors = tdsColors.map { it.getColor() },
                isSpacing = true,
                height = 25.dp,
                leftText = "Top",
            )
        }
    }
}

@Composable
private fun MonthSumCard(totalTimeSeconds: Long, goalTimeSeconds: Long, themeColor: TdsColor) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TdsCard(modifier = Modifier.size(158.dp)) {
            TdsCircularProgressIndicator(
                modifier = Modifier.size(109.dp),
                sumTime = totalTimeSeconds,
                maxTime = goalTimeSeconds,
                color = themeColor.getColor(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Month",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 11.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun WeekSumCard(totalTimeSeconds: Long, goalTimeSeconds: Long, themeColor: TdsColor) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TdsCard(
            modifier = Modifier.size(158.dp),
        ) {
            TdsCircularProgressIndicator(
                modifier = Modifier.size(109.dp),
                sumTime = totalTimeSeconds,
                maxTime = goalTimeSeconds,
                color = themeColor.getColor(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            text = "Week",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 11.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MonthCard(
    tdsColors: List<TdsColor>,
    homeMonthGraphData: HomeUiState.HomeMonthGraphData,
) {
    TdsFilledCard(
        modifier = Modifier
            .width(339.dp)
            .height(158.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TdsPieChart(
                modifier = Modifier.width(109.dp),
                taskData = homeMonthGraphData.taskData,
                totalTimeString = (homeMonthGraphData.totalTimeSeconds / 3600).toString(),
                colors = tdsColors.map { it.getColor() },
            )

            Spacer(modifier = Modifier.width(10.dp))

            TdsTaskResultList(
                modifier = Modifier.weight(1f),
                taskData = homeMonthGraphData.taskData,
                colors = tdsColors.map { it.getColor() },
                isSpacing = true,
                leftText = "Top",
                height = 25.dp,
            )
        }
    }
}

@Composable
private fun WeekCard(homeWeekGraphData: HomeUiState.HomeWeekGraphData, tdsColors: List<TdsColor>) {
    TdsFilledCard(
        modifier = Modifier
            .width(339.dp)
            .height(158.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
            ) {
                TdsText(
                    text = homeWeekGraphData.weekInformation.first,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = 25.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.width(12.dp))

                TdsText(
                    text = homeWeekGraphData.weekInformation.second,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = 25.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.width(8.dp))

                TdsText(
                    text = homeWeekGraphData.weekInformation.third,
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.TEXT,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 6.dp,
                    ),
                verticalAlignment = Alignment.Bottom,
            ) {
                TdsWeekLineChart(
                    modifier = Modifier.weight(1f),
                    weekLineChartData = homeWeekGraphData.weekLineChartData,
                    startColor = tdsColors[1].getColor(),
                    endColor = tdsColors.first().getColor(),
                )

                Column(
                    modifier = Modifier.wrapContentWidth(),
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
                        text = homeWeekGraphData.totalWeekTime,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 22.sp,
                        color = tdsColors.first(),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TdsText(
                        text = "Average",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 12.sp,
                        color = TdsColor.TEXT,
                    )

                    TdsText(
                        text = homeWeekGraphData.averageWeekTime,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 22.sp,
                        color = tdsColors.first(),
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
private fun TimeLineCard(
    tdsColors: List<TdsColor>,
    homeDailyGraphData: HomeUiState.HomeDailyGraphData,
) {
    TdsFilledCard(
        modifier = Modifier
            .width(339.dp)
            .height(158.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 10.dp,
                    horizontal = 14.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
            ) {
                TdsText(
                    text = homeDailyGraphData.todayDate,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = 24.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.width(8.dp))

                TdsDayOfTheWeek(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    todayDayOfTheWeek = homeDailyGraphData.todayDayOfTheWeek,
                    color = tdsColors.first(),
                )
            }

            TdsTimeLineChart(
                modifier = Modifier.fillMaxSize(),
                isCircleDraw = true,
                times = homeDailyGraphData.timeLines,
                startColor = tdsColors[0].getColor(),
                endColor = tdsColors[1].getColor(),
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val tdsColors = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D11,
        TdsColor.D12,
    )

    TiTiTheme {
        HomeScreen(
            tdsColors = tdsColors,
            totalData = HomeUiState.TotalData(
                totalTimeSeconds = 6368,
                topTotalTdsTaskData = listOf(
                    TdsTaskData(key = "definitiones", value = "360H", progress = 0.3f),
                    TdsTaskData(key = "231445", value = "detraxit", progress = 0.2f),
                    TdsTaskData(key = "sadfsd", value = "detraxit", progress = 0.5f),
                ),
            ),
            graphGoalTimeUiState = GraphGoalTimeUiState(),
            homeMonthGraphData = HomeUiState.HomeMonthGraphData(),
            homeWeekGraphData = HomeUiState.HomeWeekGraphData(),
            homeDailyGraphData = HomeUiState.HomeDailyGraphData(),
        )
    }
}
