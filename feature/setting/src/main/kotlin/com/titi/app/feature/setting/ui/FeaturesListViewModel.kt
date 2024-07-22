package com.titi.app.feature.setting.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.feature.setting.model.FeaturesUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FeaturesListViewModel @AssistedInject constructor(
    @Assisted initialState: FeaturesUiState,
) : MavericksViewModel<FeaturesUiState>(initialState) {

    fun updateFeatures(features: List<FeaturesUiState.Feature>) {
        setState {
            copy(features = features)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<FeaturesListViewModel, FeaturesUiState> {
        override fun create(state: FeaturesUiState): FeaturesListViewModel
    }

    companion object :
        MavericksViewModelFactory<FeaturesListViewModel, FeaturesUiState>
        by hiltMavericksViewModelFactory()
}
