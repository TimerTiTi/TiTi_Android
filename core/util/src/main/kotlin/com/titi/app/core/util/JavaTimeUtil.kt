package com.titi.app.core.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

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
