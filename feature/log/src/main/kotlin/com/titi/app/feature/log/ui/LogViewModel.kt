package com.titi.app.feature.log.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.domain.color.usecase.GetGraphColorsUseCase
import com.titi.app.domain.color.usecase.UpdateGraphColorsUseCase
import com.titi.app.feature.log.mapper.toDomainModel
import com.titi.app.feature.log.mapper.toFeatureModel
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.LogUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
    getGraphColorsUseCase: GetGraphColorsUseCase,
    private val updateGraphColorsUseCase: UpdateGraphColorsUseCase,
) : MavericksViewModel<LogUiState>(initialState) {

    init {
        getGraphColorsUseCase().catch {
            Log.e("LogViewModel", it.message.toString())
        }.filterNotNull()
            .setOnEach {
                copy(graphColors = it.toFeatureModel())
            }
    }

    fun updateGraphColors(selectedIndex: Int, graphColorUiState: GraphColorUiState) {
        viewModelScope.launch {
            updateGraphColorsUseCase(
                selectedIndex = selectedIndex,
                graphColor = graphColorUiState.toDomainModel(),
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<LogViewModel, LogUiState> {
        override fun create(state: LogUiState): LogViewModel
    }

    companion object :
        MavericksViewModelFactory<LogViewModel, LogUiState> by hiltMavericksViewModelFactory()
}
