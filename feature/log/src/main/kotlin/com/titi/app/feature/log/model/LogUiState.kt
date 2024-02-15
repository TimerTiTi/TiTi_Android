package com.titi.app.feature.log.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.GraphColor
import java.time.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

data class LogUiState(
    val graphColors: GraphColorUiState = GraphColorUiState(),
    val dailyUiState: DailyUiState = DailyUiState(),
    val weekUiState: WeekUiState = WeekUiState(),
) : MavericksState

data class GraphColorUiState(
    val selectedIndex: Int = 0,
    val direction: GraphColor.GraphDirection = GraphColor.GraphDirection.Right,
    val graphColors: List<TdsColor> = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D10,
        TdsColor.D11,
        TdsColor.D12,
    ),
)

data class DailyUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val daily: Daily? = null,
) {
    private val sumTime = daily?.tasks?.values?.sum()

    val timeLine = daily?.timeLine
    val totalTime = sumTime?.getTimeString()
    val maxTime = daily?.maxTime?.getTimeString()
    val taskData = daily?.tasks?.map {
        TdsTaskData(
            key = it.key,
            value = it.value.getTimeString(),
            progress = if (sumTime != null && sumTime > 0L) {
                it.value / sumTime.toFloat()
            } else {
                0f
            },
        )
    }
    val tdsTimeTableData = daily
        ?.taskHistories
        ?.values
        ?.flatten()
        ?.flatMap { makeTimeTableData(it.startDate, it.endDate) }
}

data class WeekUiState(
    val currentDate: LocalDate = LocalDate.now(),
)

fun makeTimeTableData(startDate: String, endDate: String): List<TdsTimeTableData> {
    var startZonedDateTime = ZonedDateTime
        .parse(startDate)
        .withZoneSameInstant(ZoneOffset.systemDefault())
    val endZonedDateTime = ZonedDateTime
        .parse(endDate)
        .withZoneSameInstant(ZoneOffset.systemDefault())

    val timeTableData = mutableListOf<TdsTimeTableData>()

    while (startZonedDateTime.isBefore(endZonedDateTime)) {
        var nextHour = startZonedDateTime.truncatedTo(ChronoUnit.HOURS).plusHours(1)
        nextHour = if (nextHour.isBefore(endZonedDateTime)) nextHour else endZonedDateTime

        timeTableData.add(
            TdsTimeTableData(
                hour = startZonedDateTime.hour,
                start = startZonedDateTime.minute * 60 + startZonedDateTime.second,
                end = nextHour.minute * 60 + nextHour.second,
            ),
        )
        startZonedDateTime = nextHour
    }

    return timeTableData.toList()
}
