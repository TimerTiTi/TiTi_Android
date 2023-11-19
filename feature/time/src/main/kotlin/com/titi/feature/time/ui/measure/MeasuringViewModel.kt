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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MeasuringUiState(
    val isSleepMode: Boolean = false,
    val measureTime: Long = 0,
) : MavericksState {
    constructor(startTime: String) : this(measureTime = getMeasureTime(startTime))
}

class MeasuringViewModel @AssistedInject constructor(
    @Assisted initialState: MeasuringUiState,
) : MavericksViewModel<MeasuringUiState>(initialState) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                setState { copy(measureTime = measureTime + 1) }
            }
        }
    }

    fun updateSleepMode() {
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