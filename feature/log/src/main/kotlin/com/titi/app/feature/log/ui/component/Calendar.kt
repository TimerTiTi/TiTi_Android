package com.titi.app.feature.log.ui.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun CalendarContent(
    modifier: Modifier = Modifier,
    themeColor: TdsColor,
    currentDate: LocalDate,
    hasDailies: List<LocalDate>,
    onClickDate: (LocalDate) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
) {
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

    LaunchedEffect(state.firstVisibleMonth.yearMonth.atDay(1)) {
        onCalendarLocalDateChanged(state.firstVisibleMonth.yearMonth.atDay(1))
    }
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
                        color = themeColor,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DaysOfWeekTitle(
                        daysOfWeek,
                        themeColor,
                    )

                    HorizontalCalendar(
                        state = state,
                        dayContent = { day ->
                            Day(
                                day = day,
                                isSelected = currentDate == day.date,
                                hasDaily = hasDailies.contains(day.date),
                                themeColor = themeColor,
                            ) { selectedDay ->
                                if (currentDate != selectedDay.date) {
                                    onClickDate(selectedDay.date)
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
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    hasDaily: Boolean,
    themeColor: TdsColor,
    onClickDate: (CalendarDay) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClickDate(day) },
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.75f)
                .clip(CircleShape)
                .background(
                    color = if (isSelected) {
                        themeColor.getColor()
                    } else {
                        Color.Transparent
                    },
                )
                .align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) {
                    if (isSelected) {
                        TdsColor.BACKGROUND.getColor()
                    } else {
                        TdsColor.TEXT.getColor()
                    }
                } else {
                    Color.Gray
                },
                style = TdsTextStyle.SEMI_BOLD_TEXT_STYLE.getTextStyle(fontSize = 18.sp),
            )
        }

        if (hasDaily) {
            Box(
                modifier = Modifier
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) {
                            themeColor.getColor()
                        } else {
                            Color.Red
                        },
                    )
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>, themeColor: TdsColor) {
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
