package com.titi.feature.time.ui.measure

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.core.util.getMeasureTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class MeasuringUiState(
    val isSleepMode: Boolean = false,
    val measureTime : Long = 0,
) : MavericksState {
    constructor(startTime : String) : this(measureTime = getMeasureTime(startTime))
}

class MeasuringViewModel @AssistedInject constructor(
    @Assisted initialState: MeasuringUiState,
) : MavericksViewModel<MeasuringUiState>(initialState) {

    fun updateSleepMode(){
        setState {
            copy(isSleepMode = !isSleepMode)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MeasuringViewModel, MeasuringUiState> {
        override fun create(state: MeasuringUiState): MeasuringViewModel
    }

    companion object :
        MavericksViewModelFactory<MeasuringViewModel, MeasuringUiState> by hiltMavericksViewModelFactory()

}