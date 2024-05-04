package com.titi.app.feature.setting.mapper

import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import com.titi.app.feature.setting.model.SettingUiState

fun NotificationRepositoryModel.toFeatureModel() = SettingUiState.SwitchState(
    timerFiveMinutesBeforeTheEnd = timerFiveMinutesBeforeTheEnd,
    timerBeforeTheEnd = timerBeforeTheEnd,
    stopwatch = stopwatch,
)
