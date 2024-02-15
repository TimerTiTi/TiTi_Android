package com.titi.app.feature.log.mapper

import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

internal fun GraphColor.toFeatureModel() = GraphColorUiState(
    selectedIndex = selectedIndex,
    direction = direction,
    graphColors = graphColors.map { TdsColor.valueOf(it.name) },
)

internal fun Daily.toFeatureModel(): DailyGraphData {
    val sumTime = tasks?.values?.sum()

    return DailyGraphData(
        totalTime = sumTime?.getTimeString() ?: "",
        maxTime = maxTime.getTimeString(),
        timeLine = timeLine,
        taskData = tasks?.map {
            TdsTaskData(
                key = it.key,
                value = it.value.getTimeString(),
                progress = if (sumTime != null && sumTime > 0L) {
                    it.value / sumTime.toFloat()
                } else {
                    0f
                },
            )
        } ?: emptyList(),
        tdsTimeTableData = taskHistories
            ?.values
            ?.flatten()
            ?.flatMap { makeTimeTableData(it.startDate, it.endDate) }
            ?: emptyList(),
        )
}

internal fun makeTimeTableData(startDate: String, endDate: String): List<TdsTimeTableData> {
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