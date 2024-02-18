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

    val uiState by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        val currentDate = LocalDate.now()
        viewModel.updateCurrentDateHome(currentDate)
        viewModel.updateCurrentDateDaily(currentDate)
        viewModel.updateCurrentDateWeek(currentDate)
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
                    totalData = uiState.homeUiState.totalData,
                    homeMonthPieData = uiState.homeUiState.homeGraphData.homeMonthPieData,
                    homeMonthGraphData = uiState.homeUiState.homeGraphData.homeMonthGraphData,
                    homeWeekPieData = uiState.homeUiState.homeGraphData.homeWeekPieData,
                    homeWeekGraphData = uiState.homeUiState.homeGraphData.homeWeekGraphData,
                    homeDailyGraphData = uiState.homeUiState.homeGraphData.homeDailyGraphData,
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
                        viewModel.updateCurrentDateWeek(it)
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
