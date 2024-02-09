package com.titi.app.feature.log.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsStandardDailyGraph
import com.titi.app.core.designsystem.component.TdsTaskProgressDailyGraph
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimeLineDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeTableDailyGraph
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
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
        CalendarContent(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))

        TdsColorRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp),
            onClick = {},
        )

        Spacer(modifier = Modifier.height(15.dp))

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

@Composable
fun CalendarContent(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val visibleYear = state.firstVisibleMonth.yearMonth.year
        val visibleMonth = state.firstVisibleMonth.yearMonth.monthValue

        TdsIconButton(
            onClick = {
                val previousYearMonth = state.firstVisibleMonth.yearMonth.minusMonths(1)

                scope.launch {
                    state.animateScrollToMonth(previousYearMonth)
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                contentDescription = "arrowLeft",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        BoxWithConstraints(
            modifier = modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            val size = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

            OutlinedCard(
                modifier = Modifier
                    .width(size)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TdsColor.BACKGROUND.getColor(),
                ),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp),
                border = BorderStroke(3.dp, TdsColor.SHADOW.getColor()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    TdsText(
                        text = "$visibleYear ${
                            visibleMonth.toString().padStart(2, '0')
                        }",
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.D1,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DaysOfWeekTitle(
                        daysOfWeek,
                        TdsColor.D1,
                    )

                    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

                    HorizontalCalendar(
                        state = state,
                        dayContent = { day ->
                            Day(
                                day = day,
                                isSelected = selectedDate == day.date,
                                themeColor = TdsColor.D1,
                            ) { selectedDay ->
                                selectedDate = if (selectedDate == selectedDay.date) {
                                    selectedDate
                                } else {
                                    selectedDay.date
                                }
                            }
                        },
                    )
                }
            }
        }

        TdsIconButton(
            onClick = {
                val nextYearMonth = state.firstVisibleMonth.yearMonth.plusMonths(1)

                scope.launch {
                    state.animateScrollToMonth(nextYearMonth)
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
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    themeColor: TdsColor,
    onClick: (CalendarDay) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                color = if (isSelected) {
                    themeColor.getColor()
                } else {
                    Color.Transparent
                },
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) {
                TdsColor.TEXT.getColor()
            } else {
                Color.Gray
            },
            style = TdsTextStyle.SEMI_BOLD_TEXT_STYLE.getTextStyle(fontSize = 18.sp),
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>, themeColor: TdsColor) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = themeColor.getColor(),
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
                    if (pagerState.currentPage - 1 >= 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
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
                    if (pagerState.currentPage + 1 < 4) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
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
