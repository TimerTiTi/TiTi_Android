package com.titi

import android.util.Log
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.domain.color.model.TimeColor
import com.titi.domain.color.usecase.GetColorUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.GetRecordTimesFlowUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch

data class MainUiState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val bottomNavigationPosition: Int = 0,
) : MavericksState

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: MainUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getColorUseCase: GetColorUseCase,
) : MavericksViewModel<MainUiState>(initialState) {

    init {
        getColorUseCase().catch {
            Log.e("MainViewModel", it.message.toString())
        }.setOnEach {
            copy(timeColor = it)
        }

        getRecordTimesFlowUseCase().catch {
            Log.e("MainViewModel", it.message.toString())
        }.setOnEach {
            copy(recordTimes = it)
        }
    }

    fun updateBottomNavigationPosition(position: Int) {
        setState {
            copy(bottomNavigationPosition = position)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel, MainUiState> {
        override fun create(state: MainUiState): MainViewModel
    }

    companion object :
        MavericksViewModelFactory<MainViewModel, MainUiState> by hiltMavericksViewModelFactory()

}