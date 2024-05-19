package com.titi.app.feature.setting.model

sealed interface SettingActions {

    sealed interface Navigates : SettingActions {
        data object FeaturesList : Navigates
        data object PlayStore : Navigates
        data object UpdatesList : Navigates
    }

    sealed interface Updates : SettingActions {
        @JvmInline
        value class Switch(val switchState: SettingUiState.SwitchState) : Updates

        @JvmInline
        value class Version(val versionState: SettingUiState.VersionState) : Updates
    }
}
