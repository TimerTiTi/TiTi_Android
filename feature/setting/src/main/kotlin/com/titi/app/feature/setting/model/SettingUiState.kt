package com.titi.app.feature.setting.model

import com.airbnb.mvrx.MavericksState

data class SettingUiState(
    val switchState: SwitchState = SwitchState(),
    val versionState: VersionState = VersionState(),
) : MavericksState {
    data class SwitchState(
        val timerFiveMinutesBeforeTheEnd: Boolean = true,
        val timerBeforeTheEnd: Boolean = true,
        val stopwatch: Boolean = true,
    )

    data class VersionState(
        val currentVersion: String = "",
        val newVersion: String = "",
    )
}
