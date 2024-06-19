package com.titi.app.feature.edit.mapper

import com.chargemap.compose.numberpicker.AMPMHours
import java.time.LocalDate
import java.time.LocalDateTime

fun AMPMHours.toLocalDateTime(currentDate: LocalDate): LocalDateTime {
    val adjustedHours = when {
        dayTime == AMPMHours.DayTime.AM && hours == 12 -> 0
        dayTime == AMPMHours.DayTime.PM && hours == 12 -> 12
        dayTime == AMPMHours.DayTime.PM -> hours + 12
        else -> hours
    }
    return currentDate.atTime(adjustedHours, minutes)
}

fun LocalDateTime.toAMPMHours(): AMPMHours {
    return AMPMHours(
        hours = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        },
        minutes = minute,
        dayTime = if (hour < 12) AMPMHours.DayTime.AM else AMPMHours.DayTime.PM,
    )
}
