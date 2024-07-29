package com.titi.app.feature.setting.mapper

import com.titi.app.data.language.api.model.LanguageRepositoryModel
import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import com.titi.app.feature.setting.model.SettingUiState

internal fun NotificationRepositoryModel.toFeatureModel() = SettingUiState.SwitchState(
    timerFiveMinutesBeforeTheEnd = timerFiveMinutesBeforeTheEnd,
    timerBeforeTheEnd = timerBeforeTheEnd,
    stopwatch = stopwatch,
)

internal fun LanguageRepositoryModel.toFeatureModel() = SettingUiState.RadioState(
    system = system,
    korean = korean,
    english = english,
    china = china,
)
