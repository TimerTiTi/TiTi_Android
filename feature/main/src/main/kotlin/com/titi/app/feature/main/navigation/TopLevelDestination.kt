package com.titi.app.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.titi.app.core.designsystem.R

enum class TopLevelDestination(
    @StringRes val titleTextId: Int,
    @DrawableRes val iconResourceId: Int,
) {
    TIMER(
        titleTextId = R.string.bottom_timer_text,
        iconResourceId = R.drawable.timer_icon,
    ),
    STOPWATCH(
        titleTextId = R.string.bottom_stopwatch_text,
        iconResourceId = R.drawable.stopwatch_icon,
    ),
    LOG(
        titleTextId = R.string.bottom_log_text,
        iconResourceId = R.drawable.log_icon,
    ),
    SETTING(
        titleTextId = R.string.bottom_setting_text,
        iconResourceId = R.drawable.setting_icon,
    ),
}
