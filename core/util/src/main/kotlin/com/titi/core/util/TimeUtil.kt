package com.titi.core.util

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun getTodayDate(): String {
    val now = LocalDate.now(ZoneId.systemDefault())
    return now.format(DateTimeFormatter.ofPattern("uuuu.MM.dd"))
}

fun addTimeToNow(time: Long): String {
    val now = LocalDateTime.now(ZoneId.systemDefault())
    val interval = Duration.ofSeconds(time)
    return now.plus(interval).format(DateTimeFormatter.ofPattern("hh.mm a"))
}

fun getTimeToLong(
    hour: String,
    minutes: String,
    seconds: String,
): Long {
    val hourLong = hour.toLongOrNull() ?: 0
    val minutesLong = minutes.toLongOrNull() ?: 0
    val secondsLong = seconds.toLongOrNull() ?: 0
    return hourLong * 3600 + minutesLong * 60 + secondsLong
}

fun isAfterSixAM(dateTime: String?): Boolean {
    return if (dateTime.isNullOrBlank()) {
        false
    } else {
        val inputDateTime = LocalDateTime.parse(dateTime)
        val currentDateTime = LocalDateTime.now()

        if (inputDateTime.dayOfMonth == currentDateTime.dayOfMonth) {
            true
        } else {
            currentDateTime.hour <= 6
        }
    }
}

fun getMeasureTime(dateTime: String): Long {
    val inputDateTime = LocalDateTime.parse(dateTime)
    val currentDateTime = LocalDateTime.now()

    return Duration.between(inputDateTime, currentDateTime).seconds
}

fun addTimeLine(
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    timeLine: List<Long>
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