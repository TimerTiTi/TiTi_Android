package com.titi.app.feature.color.ui

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.domain.color.usecase.AddBackgroundColorsUseCase
import com.titi.app.domain.color.usecase.GetBackgroundColorsUseCase
import com.titi.app.domain.color.usecase.UpdateColorUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

data class ColorUiState(
    val colors: List<Long> = emptyList()
) : MavericksState

class ColorViewModel
@AssistedInject
constructor(
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

    fun addBackgroundColor(colors: List<Long>, color: Long) {
        viewModelScope.launch {
            addBackgroundColorsUseCase(colors, color)
        }
    }

    fun updateColor(recordingMode: Int, color: Long) {
        viewModelScope.launch {
            updateColorUseCase(
                recordingMode = recordingMode,
                backgroundColor = color
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ColorViewModel, ColorUiState> {
        override fun create(state: ColorUiState): ColorViewModel
    }

    companion object :
        MavericksViewModelFactory<ColorViewModel, ColorUiState> by hiltMavericksViewModelFactory()
}
