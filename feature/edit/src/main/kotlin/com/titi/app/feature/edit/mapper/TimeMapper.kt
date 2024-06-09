package com.titi.app.feature.edit.mapper

import com.chargemap.compose.numberpicker.AMPMHours
import java.time.LocalDate
import java.time.LocalDateTime

fun AMPMHours.toLocalDateTime(currentDate: LocalDate): LocalDateTime {
    return currentDate.atTime(hours, minutes)
}

fun LocalDateTime.toAMPMHours(): AMPMHours {
    return AMPMHours(
        hours = if (hour < 12) hour else hour - 12,
        minutes = minute,
        dayTime = if (hour < 12) AMPMHours.DayTime.AM else AMPMHours.DayTime.PM,
    )
}
