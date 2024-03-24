package com.titi.app.feature.log.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsTabRow
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.ui.component.SettingBottomSheet
import java.time.LocalDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogScreen(viewModel: LogViewModel = mavericksViewModel()) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = {
            3
        },
    )

    val showSettingButton by remember {
        derivedStateOf {
            pagerState.currentPage == 0
        }
    }

    var showSettingBottomSheet by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.collectAsState()

    if (showSettingBottomSheet) {
        SettingBottomSheet(
            viewModel = viewModel,
            graphColorUiState = uiState.graphColorUiState,
            graphGoalTimeUiState = uiState.graphGoalTimeUiState,
            onDismissRequest = {
                showSettingBottomSheet = false
            },
        )
    }

    LaunchedEffect(Unit) {
        val currentDate = LocalDate.now()
        viewModel.updateCurrentDateHome(currentDate)
        viewModel.updateCurrentDateDaily(currentDate)
        viewModel.updateCurrentDateWeek(currentDate)
    }

    LaunchedEffect(uiState.tabSelectedIndex) {
        scope.launch {
            pagerState.animateScrollToPage(uiState.tabSelectedIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
            TdsTabRow(
                modifier = Modifier
                    .width(150.dp)
                    .height(30.dp)
                    .align(Alignment.Center),
                selectedItemIndex = uiState.tabSelectedIndex,
                items = listOf("Home", "Daily", "Week"),
                onClick = {
                    viewModel.updateTabSelectedIndex(it)
                },
            )

            if (showSettingButton) {
                TdsIconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        showSettingBottomSheet = true
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.setting_icon),
                        contentDescription = "setting",
                        tint = TdsColor.TEXT.getColor(),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            when (page % 3) {
                0 -> HomeScreen(
                    tdsColors = uiState.graphColorUiState.graphColors,
                    totalData = uiState.homeUiState.totalData,
                    graphGoalTimeUiState = uiState.graphGoalTimeUiState,
                    homeMonthGraphData = uiState.homeUiState.homeGraphData.homeMonthGraphData,
                    homeWeekGraphData = uiState.homeUiState.homeGraphData.homeWeekGraphData,
                    homeDailyGraphData = uiState.homeUiState.homeGraphData.homeDailyGraphData,
                )

                1 -> DailyScreen(
                    currentDate = uiState.dailyUiState.currentDate,
                    hasDailies = uiState.dailyUiState.hasDailies,
                    totalTime = uiState.dailyUiState.dailyGraphData.totalTime,
                    maxTime = uiState.dailyUiState.dailyGraphData.maxTime,
                    taskData = uiState.dailyUiState.dailyGraphData.taskData,
                    tdsColors = uiState.graphColorUiState.graphColors,
                    timeLines = uiState.dailyUiState.dailyGraphData.timeLine,
                    timeTableData = uiState.dailyUiState.dailyGraphData.tdsTimeTableData,
                    checkedButtonStates = uiState.dailyUiState.checkedButtonStates,
                    onClickDate = {
                        viewModel.updateCurrentDateDaily(it)
                    },
                    onClickGraphColor = {
                        viewModel.updateGraphColors(
                            selectedIndex = it,
                            graphColorUiState = uiState.graphColorUiState,
                        )
                    },
                    onCalendarLocalDateChanged = {
                        viewModel.updateHasDailyAtDailyTab(it)
                    },
                    onCheckedChange = { graph, checked ->
                        viewModel.updateCheckedState(
                            page = graph,
                            checked = checked,
                            checkedButtonStates = uiState.dailyUiState.checkedButtonStates,
                        )
                    },
                )

                2 -> WeekScreen(
                    weekInformation = uiState.weekUiState.weekGraphData.weekInformation,
                    hasDailies = uiState.weekUiState.hasDailies,
                    totalTime = uiState.weekUiState.weekGraphData.totalWeekTime,
                    averageTime = uiState.weekUiState.weekGraphData.averageWeekTime,
                    weekLineChartData = uiState.weekUiState.weekGraphData.weekLineChartData,
                    tdsColors = uiState.graphColorUiState.graphColors,
                    topLevelTaskTotal = uiState.weekUiState.weekGraphData.topLevelTaskTotal,
                    topLevelTaskData = uiState.weekUiState.weekGraphData.topLevelTdsTaskData,
                    currentDate = uiState.weekUiState.currentDate,
                    onClickDate = {
                        viewModel.updateCurrentDateWeek(it)
                    },
                    onClickGraphColor = {
                        viewModel.updateGraphColors(
                            selectedIndex = it,
                            graphColorUiState = uiState.graphColorUiState,
                        )
                    },
                    onCalendarLocalDateChanged = {
                        viewModel.updateHasDailyAtWeekTab(it)
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
