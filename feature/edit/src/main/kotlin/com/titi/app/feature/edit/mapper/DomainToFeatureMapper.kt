package com.titi.app.feature.edit.mapper

import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.model.TaskHistory
import com.titi.app.feature.edit.model.DailyGraphData
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

internal fun Daily.toFeatureModel(): DailyGraphData {
    val sumTime = tasks?.values?.sum()

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
            ?.values
            ?.flatten()
            ?.flatMap { makeTimeTableData(it.startDate, it.endDate) }
            ?: emptyList(),
        taskHistories = taskHistories?.mapValues { it.value.map { it.toFeatureModel() } },
    )
}

fun List<Long>.toSystemDefaultTimeLine(): List<Long> {
    val diffTime = 24 - (ZonedDateTime.now().offset.totalSeconds / 3600 + 24 + 18) % 24

    return this.subList(diffTime, 24) + this.subList(0, diffTime)
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
                end = if (nextHour.minute == 0) 3600 else nextHour.minute * 60 + nextHour.second,
            ),
        )
        startZonedDateTime = nextHour
    }

    return timeTableData.toList()
}

internal fun TaskHistory.toFeatureModel() = com.titi.app.feature.edit.model.TaskHistory(
    startDateTime = ZonedDateTime.parse(startDate)
        .withZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime(),
    endDateTime = ZonedDateTime.parse(endDate).withZoneSameInstant(ZoneOffset.systemDefault())
        .toLocalDateTime(),
)