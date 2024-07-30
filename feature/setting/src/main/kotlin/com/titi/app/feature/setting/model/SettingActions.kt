package com.titi.app.feature.setting.model

sealed interface SettingActions {

    sealed interface Navigates : SettingActions {
        data object FeaturesList : Navigates
        data object PlayStore : Navigates
        data object UpdatesList : Navigates

        @JvmInline
        value class ExternalWeb(val url: String) : Navigates
    }

    sealed interface Updates : SettingActions {
        @JvmInline
        value class Switch(val switchState: SettingUiState.SwitchState) : Updates

        @JvmInline
        value class Version(val versionState: SettingUiState.VersionState) : Updates
    }

    @JvmInline
    value class Language(val language: String) : SettingActions
}
