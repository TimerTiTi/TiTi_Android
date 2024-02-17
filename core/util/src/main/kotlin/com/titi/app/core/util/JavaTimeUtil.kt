package com.titi.app.core.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZonedDateTime

fun isCurrentWeek(checkDate: ZonedDateTime, currentDate: LocalDate): Boolean {
    val diffMonday = currentDate.dayOfWeek.value - DayOfWeek.MONDAY.value
    val diffSunday = DayOfWeek.SUNDAY.value - currentDate.dayOfWeek.value

    val monday = currentDate
        .minusDays(diffMonday.toLong())
        .atStartOfDay()
        .atZone(java.time.ZoneOffset.systemDefault())
        .withZoneSameInstant(java.time.ZoneOffset.UTC)

    val sunday = currentDate
        .plusDays(diffSunday.toLong())
        .atTime(23, 59, 59)
        .atZone(java.time.ZoneOffset.systemDefault())
        .withZoneSameInstant(java.time.ZoneOffset.UTC)

    return checkDate.isAfter(monday) && checkDate.isBefore(sunday)
}
