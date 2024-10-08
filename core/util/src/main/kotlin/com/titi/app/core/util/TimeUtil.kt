package com.titi.app.core.util

import org.threeten.bp.Duration
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

fun String.parseZoneDateTime(): String {
    val inputDateTime = ZonedDateTime
        .parse(this)
        .withZoneSameInstant(ZoneId.systemDefault())
    return inputDateTime.format(DateTimeFormatter.ofPattern("uuuu.MM.dd"))
}

fun addTimeToNow(time: Long): String {
    val now = ZonedDateTime.now()
    val interval = Duration.ofSeconds(time)
    return now.plus(interval).format(DateTimeFormatter.ofPattern("HH.mm"))
}

fun getTimeToLong(hour: String, minutes: String, seconds: String): Long {
    val hourLong = hour.toLongOrNull() ?: 0
    val minutesLong = minutes.toLongOrNull() ?: 0
    val secondsLong = seconds.toLongOrNull() ?: 0
    return hourLong * 3600 + minutesLong * 60 + secondsLong
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
