package com.titi.app.feature.setting.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.feature.setting.model.SettingUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SettingViewModel @AssistedInject constructor(
    @Assisted initialState: SettingUiState,
) : MavericksViewModel<SettingUiState>(initialState) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<SettingViewModel, SettingUiState> {
        override fun create(state: SettingUiState): SettingViewModel
    }

    companion object :
        MavericksViewModelFactory<SettingViewModel, SettingUiState>
        by hiltMavericksViewModelFactory()
}
