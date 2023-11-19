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
    if (dateTime.isNullOrBlank()) {
        return false
    } else {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val dateTime = LocalDateTime.parse(dateTime, formatter)
        val todaySixAM = LocalDateTime.now().withHour(6).withMinute(0)

        return dateTime.isAfter(todaySixAM)
    }
}

fun getMeasureTime(dateTime: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    val dateTime = LocalDateTime.parse(dateTime, formatter)
    val todayTime = LocalDateTime.now()

    return Duration.between(dateTime, todayTime).seconds
}