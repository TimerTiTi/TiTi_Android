package com.titi.feature.time.ui.time

import android.util.Log
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.core.util.getTodayDate
import com.titi.domain.color.model.TimeColor
import com.titi.domain.color.usecase.GetColorUseCase
import com.titi.domain.color.usecase.UpdateColorUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.domain.time.usecase.UpdateRecordingModeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class TimeUiState(
    val todayDate: String = "",
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
) : MavericksState

class TimeViewModel @AssistedInject constructor(
    @Assisted initialState: TimeUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    getColorUseCase: GetColorUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
) : MavericksViewModel<TimeUiState>(initialState) {

    init {
        setState {
            copy(todayDate = getTodayDate())
        }

        getRecordTimesFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(recordTimes = it)
        }

        getColorUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(timeColor = it)
        }
    }

    fun updateRecordingMode(recordingMode: Int) {
        viewModelScope.launch {
            updateRecordingModeUseCase(recordingMode)
        }
    }

    fun updateColor(timeColor: TimeColor) {
        viewModelScope.launch {
            updateColorUseCase(timeColor = timeColor)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TimeViewModel, TimeUiState> {
        override fun create(state: TimeUiState): TimeViewModel
    }

    companion object :
        MavericksViewModelFactory<TimeViewModel, TimeUiState> by hiltMavericksViewModelFactory()

}