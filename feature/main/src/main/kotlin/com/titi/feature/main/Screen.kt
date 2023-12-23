package com.titi.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.titi.designsystem.R

sealed class Screen(
    val route: String,
    @StringRes val stringResourceId: Int,
    @DrawableRes val drawableResourceId: Int,
) {

    data object Timer : Screen("Timer", R.string.timer, R.drawable.timer_icon)

    data object StopWatch :
        Screen("StopWatch", R.string.stopwatch, R.drawable.stopwatch_icon)

}