package com.titi.app.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

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
    val zonedDateTimeToLocalDate = zonedDateTime.toLocalDate()

    val weekFields = WeekFields.of(Locale.getDefault())

    val localDateWeek = localDate.get(weekFields.weekOfWeekBasedYear())
    val zonedDateTimeWeek = zonedDateTimeToLocalDate.get(weekFields.weekOfWeekBasedYear())

    val localDateYear = localDate.get(weekFields.weekBasedYear())
    val zonedDateTimeYear = zonedDateTimeToLocalDate.get(weekFields.weekBasedYear())

    return localDateWeek == zonedDateTimeWeek && localDateYear == zonedDateTimeYear
}

fun areDatesInSameDay(localDate: LocalDate, zonedDateTime: ZonedDateTime): Boolean {
    val zonedDateTimeToLocalDate = zonedDateTime.toLocalDate()

    return localDate == zonedDateTimeToLocalDate
}
