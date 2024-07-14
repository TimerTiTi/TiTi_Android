package com.titi.app.core.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toOnlyTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return this.format(formatter)
}

fun getDailyDayWithHour(hour: Int): Pair<String, String> {
    val currentDateTime = LocalDateTime.now()

    return if (currentDateTime.hour < hour) {
        val startDateTime = currentDateTime
            .minusDays(1)
            .withHour(hour)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()
        val endDateTime = currentDateTime
            .withHour(hour)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()

        startDateTime to endDateTime
    } else {
        val startDateTime = currentDateTime
            .withHour(hour)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()
        val endDateTime = currentDateTime
            .plusDays(1)
            .withHour(hour)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()

        startDateTime to endDateTime
    }
}

fun areDatesInSameMonth(localDate: LocalDate, zonedDateTime: ZonedDateTime): Boolean {
    val zonedDateTimeToLocalDate = zonedDateTime.toLocalDate()

    return localDate.year == zonedDateTimeToLocalDate.year &&
        localDate.month == zonedDateTimeToLocalDate.month
}

fun areDatesInSameWeek(localDate: LocalDate, zonedDateTime: ZonedDateTime): Boolean {
    val (monday, sunday) = getMondaySunday(localDate)

    return zonedDateTime.isAfter(monday) && zonedDateTime.isBefore(sunday)
}

fun areDatesInSameDay(localDate: LocalDate, zonedDateTime: ZonedDateTime): Boolean {
    val zonedDateTimeToLocalDate = zonedDateTime.toLocalDate()

    return localDate == zonedDateTimeToLocalDate
}

fun getMondaySunday(currentDate: LocalDate): Pair<ZonedDateTime, ZonedDateTime> {
    val diffMonday = currentDate.dayOfWeek.value - DayOfWeek.MONDAY.value
    val diffSunday = DayOfWeek.SUNDAY.value - currentDate.dayOfWeek.value

    val monday = currentDate
        .minusDays(diffMonday.toLong())
        .atStartOfDay(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    val sunday = currentDate
        .plusDays(diffSunday.toLong())
        .atTime(23, 59, 59)
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)

    return Pair(monday, sunday)
}
