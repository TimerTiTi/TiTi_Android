package com.titi.feature.time

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.domain.color.model.TimeColor
import com.titi.domain.color.usecase.AddBackgroundColorsUseCase
import com.titi.domain.color.usecase.GetBackgroundColorsUseCase
import com.titi.domain.color.usecase.UpdateColorUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

data class ColorUiState(
    val colors: List<Long> = emptyList()
) : MavericksState

class ColorViewModel @AssistedInject constructor(
    @Assisted initialState: ColorUiState,
    getBackgroundColorsUseCase: GetBackgroundColorsUseCase,
    private val addBackgroundColorsUseCase: AddBackgroundColorsUseCase,
    private val updateColorUseCase: UpdateColorUseCase
) : MavericksViewModel<ColorUiState>(initialState) {

    init {
        suspend {
            getBackgroundColorsUseCase()
        }.execute {
            copy(colors = it.invoke()?.colors ?: emptyList())
        }
    }

    fun addBackgroundColor(
        colors: List<Long>,
        color: Long
    ) {
        viewModelScope.launch {
            addBackgroundColorsUseCase(colors, color)
        }
    }

    fun updateColor(
        recordingMode: Int,
        timeColor: TimeColor,
        color: Long,
    ) {
        viewModelScope.launch {
            val updateTimeColor = if (recordingMode == 1) {
                timeColor.copy(timerBackgroundColor = color)
            } else {
                timeColor.copy(stopwatchBackgroundColor = color)
            }

            updateColorUseCase(updateTimeColor)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ColorViewModel, ColorUiState> {
        override fun create(state: ColorUiState): ColorViewModel
    }

    companion object :
        MavericksViewModelFactory<ColorViewModel, ColorUiState> by hiltMavericksViewModelFactory()

}