package com.titi.app.core.designsystem.extension

import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

fun LocalDate.getWeekInformation(): Triple<String, String, String> {
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

fun LocalDate.makeDefaultWeekLineChardData(): List<TdsWeekLineChartData> {
    val diffMonday = this.dayOfWeek.value - DayOfWeek.MONDAY.value
    var startDay = this.minusDays(diffMonday.toLong())

    val weekLineChartData = mutableListOf<TdsWeekLineChartData>()
    repeat(7) {
        weekLineChartData.add(
            TdsWeekLineChartData(
                time = 0L,
                date = "${startDay.monthValue}/${startDay.dayOfMonth}",
            ),
        )
    }

    return weekLineChartData.toList()
}