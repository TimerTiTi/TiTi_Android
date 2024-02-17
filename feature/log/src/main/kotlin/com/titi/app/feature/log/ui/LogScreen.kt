package com.titi.app.feature.log.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.component.TdsTabRow
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TiTiTheme
import java.time.LocalDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogScreen(viewModel: LogViewModel = mavericksViewModel()) {
    val scope = rememberCoroutineScope()
    var tabSelectedIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState(
        pageCount = {
            3
        },
    )

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

    val timeLines = listOf(
        3600L,
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
    )

    val weekLineChardData = listOf(
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
    )

    val uiState by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        val currentDate = LocalDate.now()
        viewModel.updateCurrentDateDaily(currentDate)
        viewModel.updateWeekCurrentDate(currentDate)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TdsTabRow(
            modifier = Modifier
                .width(150.dp)
                .height(30.dp),
            selectedItemIndex = tabSelectedIndex,
            items = listOf("Home", "Daily", "Week"),
            onClick = {
                tabSelectedIndex = it
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            },
        )

        Spacer(modifier = Modifier.height(15.dp))

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            when (page % 3) {
                0 -> HomeScreen(
                    tdsColors = uiState.graphColors.graphColors,
                    taskData = taskData,
                    weekLineChardData = weekLineChardData,
                    timeLines = timeLines,
                )

                1 -> DailyScreen(
                    currentDate = uiState.dailyUiState.currentDate,
                    totalTime = uiState.dailyUiState.dailyGraphData.totalTime,
                    maxTime = uiState.dailyUiState.dailyGraphData.maxTime,
                    taskData = uiState.dailyUiState.dailyGraphData.taskData,
                    tdsColors = uiState.graphColors.graphColors,
                    timeLines = uiState.dailyUiState.dailyGraphData.timeLine,
                    timeTableData = uiState.dailyUiState.dailyGraphData.tdsTimeTableData,
                    onClickDate = {
                        viewModel.updateCurrentDateDaily(it)
                    },
                    onClickGraphColor = {
                        viewModel.updateGraphColors(
                            selectedIndex = it,
                            graphColorUiState = uiState.graphColors,
                        )
                    },
                )

                2 -> WeekScreen(
                    weekInformation = uiState.weekUiState.weekGraphData.weekInformation,
                    totalTime = uiState.weekUiState.weekGraphData.totalWeekTime,
                    averageTime = uiState.weekUiState.weekGraphData.averageWeekTime,
                    weekLineChardData = uiState.weekUiState.weekGraphData.weekLineChartData,
                    tdsColors = uiState.graphColors.graphColors,
                    topLevelTaskTotal = uiState.weekUiState.weekGraphData.topLevelTaskTotal,
                    topLevelTaskData = uiState.weekUiState.weekGraphData.topLevelTdsTaskData,
                    currentDate = uiState.weekUiState.currentDate,
                    onClickDate = {
                        viewModel.updateWeekCurrentDate(it)
                    },
                    onClickGraphColor = {
                        viewModel.updateGraphColors(
                            selectedIndex = it,
                            graphColorUiState = uiState.graphColors,
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun LogScreenPreview() {
    TiTiTheme {
        LogScreen()
    }
}
