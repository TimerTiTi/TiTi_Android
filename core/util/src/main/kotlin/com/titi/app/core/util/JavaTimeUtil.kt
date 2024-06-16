package com.titi.app.core.util

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun isCurrentWeek(checkDate: ZonedDateTime, currentDate: LocalDate): Boolean {
    val diffMonday = currentDate.dayOfWeek.value - DayOfWeek.MONDAY.value
    val diffSunday = DayOfWeek.SUNDAY.value - currentDate.dayOfWeek.value

    val monday = currentDate
        .minusDays(diffMonday.toLong())
        .atStartOfDay()
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    val sunday = currentDate
        .plusDays(diffSunday.toLong())
        .atTime(23, 59, 59)
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    return checkDate.isAfter(monday) && checkDate.isBefore(sunday)
}

fun isCurrentDaily(checkDate: ZonedDateTime, currentDate: LocalDate): Boolean {
    val startCurrentDay = currentDate
        .atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    val endCurrentDay = currentDate
        .atTime(23, 59, 59)
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    return checkDate.isAfter(startCurrentDay) && checkDate.isBefore(endCurrentDay)
}

fun LocalDateTime.toOnlyTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return this.format(formatter)
}

fun addTimeLine(
    startTime: ZonedDateTime,
    endTime: ZonedDateTime,
    timeLine: List<Long>,
): List<Long> {
    var current = startTime
    val updateTimeLine = timeLine.toMutableList()

    while (current.isBefore(endTime)) {
        val diffSeconds = if (current.hour == endTime.hour) {
            Duration.between(current, endTime).seconds
        } else {
            val nextTime = current.plusHours(1).withMinute(0).withSecond(0)
            Duration.between(current, nextTime).seconds
        }

        updateTimeLine[current.hour] += diffSeconds
        current = current.plusHours(1).withMinute(0).withSecond(0)
    }

    return updateTimeLine.toList()
}
