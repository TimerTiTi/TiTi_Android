package com.titi.app.feature.log.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.feature.log.model.LogUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
) : MavericksViewModel<LogUiState>(initialState) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<LogViewModel, LogUiState> {
        override fun create(state: LogUiState): LogViewModel

        companion object :
            MavericksViewModelFactory<LogViewModel, LogUiState> by hiltMavericksViewModelFactory()
    }
}
