package com.titi.app.feature.log.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsStandardDailyGraph
import com.titi.app.core.designsystem.component.TdsTaskProgressDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeLineDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeTableDailyGraph
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import kotlinx.coroutines.launch

@Composable
fun DailyScreen(
    todayDate: String,
    todayDayOfTheWeek: Int,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Int>,
    timeTableData: List<TdsTimeTableData>,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        CalendarContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = todayDate,
            todayDayOfTheWeek = todayDayOfTheWeek,
            taskData = taskData,
            tdsColors = tdsColors,
            timeLines = timeLines,
            timeTableData = timeTableData,
        )

        Spacer(modifier = Modifier.height(40.dp))

        GraphContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = todayDate,
            todayDayOfTheWeek = todayDayOfTheWeek,
            taskData = taskData,
            tdsColors = tdsColors,
            timeLines = timeLines,
            timeTableData = timeTableData,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Int>,
    timeTableData: List<TdsTimeTableData>,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            4
        },
    )
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TdsIconButton(
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(
                        if (pagerState.currentPage - 1 < 0) {
                            3
                        } else {
                            pagerState.currentPage - 1
                        },
                    )
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                contentDescription = "arrowLeft",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        HorizontalPager(
            modifier = Modifier.weight(1f),
            userScrollEnabled = true,
            state = pagerState,
        ) { page ->
            when (page % 4) {
                0 -> TdsStandardDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    taskData = taskData,
                )

                1 -> TdsTimeTableDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    taskData = taskData,
                    timeTableData = timeTableData,
                )

                2 -> TdsTimeLineDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    totalTime = timeLines.sum().toLong().getTimeString(),
                    maxTime = timeLines.max().toLong().getTimeString(),
                )

                3 -> TdsTaskProgressDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    taskData = taskData,
                    tdsColors = tdsColors,
                )
            }
        }

        TdsIconButton(
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(
                        if (pagerState.currentPage + 1 > 3) {
                            0
                        } else {
                            pagerState.currentPage + 1
                        },
                    )
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right_icon),
                contentDescription = "arrowRight",
                tint = TdsColor.TEXT.getColor(),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GraphContent(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Int>,
    timeTableData: List<TdsTimeTableData>,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            4
        },
    )
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TdsIconButton(
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(
                        if (pagerState.currentPage - 1 < 0) {
                            3
                        } else {
                            pagerState.currentPage - 1
                        },
                    )
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                contentDescription = "arrowLeft",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        HorizontalPager(
            modifier = Modifier.weight(1f),
            userScrollEnabled = true,
            state = pagerState,
        ) { page ->
            when (page % 4) {
                0 -> TdsStandardDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    taskData = taskData,
                )

                1 -> TdsTimeTableDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    taskData = taskData,
                    timeTableData = timeTableData,
                )

                2 -> TdsTimeLineDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    totalTime = timeLines.sum().toLong().getTimeString(),
                    maxTime = timeLines.max().toLong().getTimeString(),
                )

                3 -> TdsTaskProgressDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    taskData = taskData,
                    tdsColors = tdsColors,
                )
            }
        }

        TdsIconButton(
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(
                        if (pagerState.currentPage + 1 > 3) {
                            0
                        } else {
                            pagerState.currentPage + 1
                        },
                    )
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right_icon),
                contentDescription = "arrowRight",
                tint = TdsColor.TEXT.getColor(),
            )
        }
    }
}

@Composable
@Preview
private fun DailyScreenPreview() {
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

    val timeLines = listOf(
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
    )

    val timeTableData = listOf(
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
    )

    val todayDate = "2024.02.04"
    val todayDayOfTheWeek = 6

    TiTiTheme {
        DailyScreen(
            todayDate = todayDate,
            todayDayOfTheWeek = todayDayOfTheWeek,
            taskData = taskData,
            tdsColors = tdsColors,
            timeLines = timeLines,
            timeTableData = timeTableData,
        )
    }
}
