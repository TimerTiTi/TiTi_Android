package com.titi.designsystem.extension

import com.titi.designsystem.model.TdsTime

fun Long.getTdsTime() = TdsTime(
    hour = (this / 3600).toInt(),
    minutes = (this % 3600 / 60).toInt(),
    seconds = (this % 60).toInt()
)