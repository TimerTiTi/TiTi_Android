package com.titi.feature.time

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.data.time.repository.model.RecordTimes
import com.titi.feature.time.usecase.GetRecordTimesFlowUseCase
import com.titi.feature.time.usecase.UpdateRecordingModeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class TimeUiState(
    val todayDate: String = "",
    val recordTimes: RecordTimes = RecordTimes()
) : MavericksState

class TimeViewModel @AssistedInject constructor(
    @Assisted initialState: TimeUiState,
    private val getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
) : MavericksViewModel<TimeUiState>(initialState) {

    init {
        viewModelScope.launch {
            getRecordTimesFlowUseCase().collectLatest {
                it?.let {
                    setState {
                        copy(recordTimes = it)
                    }
                }
            }
        }

    }

    fun updateRecordingMode(recordingMode: Int) {
        viewModelScope.launch {
            updateRecordingModeUseCase(recordingMode = recordingMode)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TimeViewModel, TimeUiState> {
        override fun create(state: TimeUiState): TimeViewModel
    }

    companion object :
        MavericksViewModelFactory<TimeViewModel, TimeUiState> by hiltMavericksViewModelFactory()

}