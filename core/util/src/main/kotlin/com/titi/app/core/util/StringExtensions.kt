package com.titi.app.core.util

import java.time.ZoneId
import java.time.ZonedDateTime

fun String.isAfterH(hour: Int): Boolean {
    val inputDateTime = ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault())
    val currentDateTime = ZonedDateTime.now()

    return inputDateTime.dayOfMonth != currentDateTime.dayOfMonth && currentDateTime.hour >= hour
}

fun String.containsSpecialCharacter(): Boolean {
    val specialCharacters = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
    return this.any { it in specialCharacters }
}