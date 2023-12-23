package com.titi.feature.main.ui.main

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.feature.main.model.MainUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: MainUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
) : MavericksViewModel<MainUiState>(initialState) {

    init {
        getTimeColorFlowUseCase().catch {
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