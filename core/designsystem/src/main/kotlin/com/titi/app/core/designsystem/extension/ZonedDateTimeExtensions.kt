package com.titi.app.core.designsystem.extension

import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters

fun ZonedDateTime.getWeekInformation(): Triple<String, String, String> {
    val firstDayOfMonth = this.with(TemporalAdjusters.firstDayOfMonth())
    val firstMondayOfMonth = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
    val week = (this.dayOfYear - firstMondayOfMonth.dayOfYear) / 7 + 1

    val firstDayOfWeek = this.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val lastDayOfWeek = this.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val formatter = DateTimeFormatter.ofPattern("MM.dd")

    return Triple(
        String.format("%d.%02d", this.year, this.monthValue),
        "Week $week",
        "${firstDayOfWeek.format(formatter)} ~ ${lastDayOfWeek.format(formatter)}",
    )
}
