package com.titi.app.feature.setting.mapper

import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import com.titi.app.feature.setting.model.SettingUiState

internal fun SettingUiState.SwitchState.toRepositoryModel() = NotificationRepositoryModel(
    timerFiveMinutesBeforeTheEnd = timerFiveMinutesBeforeTheEnd,
    timerBeforeTheEnd = timerBeforeTheEnd,
    stopwatch = stopwatch,
)
