package com.titi.app.feature.setting.model

import com.airbnb.mvrx.MavericksState

data class SettingUiState(
    val switchState: SwitchState = SwitchState(),
    val radioState: RadioState = RadioState(),
    val versionState: VersionState = VersionState(),
) : MavericksState {
    data class SwitchState(
        val timerFiveMinutesBeforeTheEnd: Boolean = true,
        val timerBeforeTheEnd: Boolean = true,
        val stopwatch: Boolean = true,
    )

    data class RadioState(
        val system: Boolean = true,
        val korean: Boolean = false,
        val english: Boolean = false,
        val china: Boolean = false,
    )

    data class VersionState(
        val currentVersion: String = "",
        val newVersion: String = "",
    )
}
