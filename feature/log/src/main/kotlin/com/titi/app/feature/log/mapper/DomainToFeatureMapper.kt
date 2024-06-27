package com.titi.app.feature.log.mapper

import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.extension.makeDefaultWeekLineChardData
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.util.isCurrentDaily
import com.titi.app.core.util.isCurrentWeek
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.HomeUiState
import com.titi.app.feature.log.model.WeekGraphData
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

internal fun GraphColor.toFeatureModel() = GraphColorUiState(
    selectedIndex = selectedIndex,
    direction = direction,
    graphColors = graphColors.map { TdsColor.valueOf(it.name) },
)

internal fun Daily.toFeatureModel(colors: List<TdsColor>): DailyGraphData {
    val sumTime = tasks?.values?.sum()
    var colorIndex = 0
    val taskColorMap = mutableMapOf<String, TdsColor>()

    return DailyGraphData(
        totalTime = sumTime?.getTimeString() ?: "00:00:00",
        maxTime = maxTime.getTimeString(),
        timeLine = timeLine.toSystemDefaultTimeLine(),
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
            ?.flatMap { (taskName, histories) ->
                histories.map { history ->
                    val color = taskColorMap.getOrPut(taskName) {
                        colors[colorIndex++ % colors.size]
                    }

                    makeTimeTableData(
                        color = color,
                        startDate = history.startDate,
                        endDate = history.endDate,
                    )
                }.flatten()
            } ?: emptyList(),
    )
}

internal fun makeTimeTableData(
    color: TdsColor,
    startDate: String,
    endDate: String,
): List<TdsTimeTableData> {
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
                color = color,
                hour = startZonedDateTime.hour,
                start = startZonedDateTime.minute * 60 + startZonedDateTime.second,
                end = if (nextHour.minute == 0) 3600 else nextHour.minute * 60 + nextHour.second,
            ),
        )
        startZonedDateTime = nextHour
    }

    return timeTableData.toList()
}

internal fun List<Daily>.toWeekFeatureModel(currentDate: LocalDate): WeekGraphData {
    val defaultWeekLineChartData = currentDate
        .makeDefaultWeekLineChardData()
        .toMutableList()

    var totalWeekTime = 0L
    var studyCount = 0
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

        defaultWeekLineChartData[dateTime.dayOfWeek.value - 1] = updateWeekLineChartData
        totalWeekTime += sumTime
        if (sumTime > 0) {
            studyCount++
        }
        daily.tasks?.let { taskMap ->
            taskMap.forEach { (taskName, taskTime) ->
                totalTaskMap[taskName] = totalTaskMap.getOrDefault(taskName, 0L) + taskTime
            }
        }
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
        averageWeekTime = if (studyCount > 0) {
            (totalWeekTime / studyCount).getTimeString()
        } else {
            0L.getTimeString()
        },
        weekLineChartData = defaultWeekLineChartData.toList(),
        topLevelTaskTotal = topLevelTaskSum.getTimeString(),
        topLevelTdsTaskData = topLevelTdsTaskData,
    )
}

internal fun Pair<Map<String, Long>, List<Daily>>.toHomeFeatureModel(
    currentDate: LocalDate,
): HomeUiState {
    val totalMap = this.first
    val totalTaskSum = totalMap.values.sum()
    val totalTopLevelTask = totalMap
        .toList()
        .sortedByDescending { it.second }
        .take(5)
    val totalTopLevelTaskSum = totalTopLevelTask.sumOf { it.second }
    val totalTopLevelTdsTaskData = totalTopLevelTask.map {
        TdsTaskData(
            key = it.first,
            value = it.second.getTimeString(),
            progress = if (totalTopLevelTaskSum == 0L) {
                0f
            } else {
                it.second / totalTopLevelTaskSum.toFloat()
            },
        )
    }

    val monthDailies = this.second
    val weekDailies =
        monthDailies.filter { isCurrentWeek(ZonedDateTime.parse(it.day), currentDate) }
    val todayDaily =
        weekDailies.firstOrNull { isCurrentDaily(ZonedDateTime.parse(it.day), currentDate) }

    val monthTaskMap = mutableMapOf<String, Long>()
    var monthTotalTime = 0L
    monthDailies.forEach { daily ->
        daily.tasks?.let { task ->
            task.forEach { (taskName, taskTime) ->
                monthTaskMap[taskName] = monthTaskMap.getOrDefault(taskName, 0L) + taskTime
            }
            monthTotalTime += task.values.sum()
        }
    }
    val monthTopTaskData = monthTaskMap
        .toList()
        .sortedByDescending { it.second }
        .take(5)
    val monthTopTaskSum = monthTopTaskData.sumOf { it.second }
    val monthTopTdsTaskData = monthTopTaskData.map {
        TdsTaskData(
            key = it.first,
            value = it.second.getTimeString(),
            progress = if (monthTopTaskSum == 0L) 0f else it.second / monthTopTaskSum.toFloat(),
        )
    }

    var weekTotalTime = 0L
    var weekStudyCount = 0
    val weekLineChartData = currentDate.makeDefaultWeekLineChardData().toMutableList()

    weekDailies.forEach { daily ->
        val dateTime = ZonedDateTime
            .parse(daily.day)
            .withZoneSameInstant(ZoneId.systemDefault())
        val sumTime = daily.tasks?.values?.sum() ?: 0L

        val updateWeekLineChartData = TdsWeekLineChartData(
            time = sumTime,
            date = "${dateTime.month.value}/${dateTime.dayOfMonth}",
        )

        weekLineChartData[dateTime.dayOfWeek.value - 1] = updateWeekLineChartData
        weekTotalTime += sumTime
        if (sumTime > 0) {
            weekStudyCount++
        }
    }

    return HomeUiState(
        totalData = HomeUiState.TotalData(
            totalTimeSeconds = totalTaskSum,
            topTotalTdsTaskData = totalTopLevelTdsTaskData,
        ),
        homeGraphData = HomeUiState.HomeGraphData(
            homeMonthGraphData = HomeUiState.HomeMonthGraphData(
                totalTimeSeconds = monthTotalTime,
                taskData = monthTopTdsTaskData,
            ),
            homeWeekGraphData = HomeUiState.HomeWeekGraphData(
                weekInformation = currentDate.getWeekInformation(),
                totalTimeSeconds = weekTotalTime,
                totalWeekTime = weekTotalTime.getTimeString(),
                averageWeekTime = (weekTotalTime / weekStudyCount).getTimeString(),
                weekLineChartData = weekLineChartData,
            ),
            homeDailyGraphData = HomeUiState.HomeDailyGraphData(
                currentDate = currentDate,
                timeLines = todayDaily?.timeLine?.toSystemDefaultTimeLine()
                    ?: LongArray(24) { 0L }.toList(),
            ),
        ),
    )
}

fun List<Long>.toSystemDefaultTimeLine(): List<Long> {
    val diffTime = 24 - (ZonedDateTime.now().offset.totalSeconds / 3600 + 24 + 18) % 24

    return this.subList(diffTime, 24) + this.subList(0, diffTime)
}
