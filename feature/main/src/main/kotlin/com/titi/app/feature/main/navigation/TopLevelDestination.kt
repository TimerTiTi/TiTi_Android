package com.titi.app.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.titi.app.core.designsystem.R

enum class TopLevelDestination(
    @StringRes val titleTextId: Int,
    @DrawableRes val iconResourceId: Int
) {
    TIMER(
        titleTextId = R.string.timer,
        iconResourceId = R.drawable.timer_icon
    ),
    STOPWATCH(
        titleTextId = R.string.stopwatch,
        iconResourceId = R.drawable.stopwatch_icon
    )
}
