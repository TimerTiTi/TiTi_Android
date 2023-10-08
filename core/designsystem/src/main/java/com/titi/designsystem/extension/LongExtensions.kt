package com.titi.designsystem.extension

import com.titi.designsystem.model.TdsTime

fun Long.getTdsTime() = TdsTime(
    hour = (this / 3600).toInt(),
    minutes = (this % 3600 / 60).toInt(),
    seconds = (this % 60).toInt()
)

fun Long.getTimeString(): String {
    val hour = (this / 3600).toInt()
    val minutes = (this % 3600 / 60).toInt()
    val seconds = (this % 60).toInt()

    return "${hour.toString().padStart(2, '0')}:${
        minutes.toString().padStart(2, '0')
    }:${seconds.toString().padStart(2, '0')}"
}