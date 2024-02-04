package com.titi.app.core.designsystem.util

import com.titi.app.core.designsystem.model.TdsTaskData
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

fun List<TdsTaskData>.getMaxTime(): String {
    val localTimes = this.map { LocalTime.parse(it.value) }

    return localTimes
        .maxOrNull()
        ?.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        ?.toString()
        ?: "00:00:00"
}

fun List<TdsTaskData>.getSumTime(): String {
    val localTimes = this.map { LocalTime.parse(it.value) }
    var sumTime = LocalTime.of(0, 0, 0)

    for (time in localTimes) {
        sumTime = sumTime.plusHours(time.hour.toLong())
        sumTime = sumTime.plusMinutes(time.minute.toLong())
        sumTime = sumTime.plusSeconds(time.second.toLong())
    }

    return sumTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}
