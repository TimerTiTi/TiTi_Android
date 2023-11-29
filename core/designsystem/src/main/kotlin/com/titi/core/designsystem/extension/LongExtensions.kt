package com.titi.core.designsystem.extension

import com.titi.core.designsystem.model.TdsTime

fun Long.getTdsTime(): TdsTime {
    val time = if (this < 0) {
        -this
    } else {
        this
    }
    return TdsTime(
        hour = (time / 3600).toInt(),
        minutes = (time % 3600 / 60).toInt(),
        seconds = (time % 60).toInt()
    )
}

fun Long.getTimeString(): String {
    val hour = (this / 3600).toInt()
    val minutes = (this % 3600 / 60).toInt()
    val seconds = (this % 60).toInt()

    return "${hour.toString().padStart(2, '0')}:${
        minutes.toString().padStart(2, '0')
    }:${seconds.toString().padStart(2, '0')}"
}