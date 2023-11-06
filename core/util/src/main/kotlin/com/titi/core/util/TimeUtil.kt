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