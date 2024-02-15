package com.titi.app.feature.log.mapper

import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.extension.makeDefaultWeekLineChardData
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.WeekGraphData
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.max

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

internal fun List<Daily>.toFeatureModel(currentDate: LocalDate): WeekGraphData {
    val defaultWeekLineChartData = currentDate
        .makeDefaultWeekLineChardData()
        .toMutableList()

    var totalWeekTime = 0L
    var maxWeekTime = 0L
    val totalTaskMap = mutableMapOf<String, Long>()

    this.forEach { daily ->
        val dateTime = ZonedDateTime
            .parse(daily.day)
            .withZoneSameInstant(ZoneId.systemDefault())
        val sumTime = daily.tasks?.values?.sum() ?: 0L

        val updateWeekLineChartData = TdsWeekLineChartData(
            time = sumTime,
            date = "${dateTime.month.value}/${dateTime.dayOfMonth}",
        )

        defaultWeekLineChartData[dateTime.dayOfWeek.value] = updateWeekLineChartData
        totalWeekTime += sumTime
        maxWeekTime = max(maxWeekTime, sumTime)
        daily.tasks?.let { taskMap -> totalTaskMap.putAll(taskMap) }
    }

    val topLevelTask = totalTaskMap
        .toList()
        .sortedByDescending { it.second }
        .take(5)
    val topLevelTaskSum = topLevelTask.sumOf { it.second }

    val topLevelTdsTaskData = topLevelTask.map {
        TdsTaskData(
            key = it.first,
            value = it.second.getTimeString(),
            progress = if (topLevelTaskSum == 0L) 0f else it.second / topLevelTaskSum.toFloat(),
        )
    }
    return WeekGraphData(
        weekInformation = currentDate.getWeekInformation(),
        totalWeekTime = totalWeekTime.getTimeString(),
        maxWeekTime = maxWeekTime.getTimeString(),
        weekLineChartData = defaultWeekLineChartData.toList(),
        topLevelTaskTotal = topLevelTaskSum.getTimeString(),
        topLevelTdsTaskData = topLevelTdsTaskData,
    )
}