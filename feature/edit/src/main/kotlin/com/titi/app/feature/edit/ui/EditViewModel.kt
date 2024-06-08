package com.titi.app.feature.edit.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.usecase.GetCurrentDateDailyFlowUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsUseCase
import com.titi.app.feature.edit.mapper.toFeatureModel
import com.titi.app.feature.edit.model.DailyGraphData
import com.titi.app.feature.edit.model.EditUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EditViewModel @AssistedInject constructor(
    @Assisted initialState: EditUiState,
    private val getGraphColorsUseCase: GetGraphColorsUseCase,
    getCurrentDateDailyFlowUseCase: GetCurrentDateDailyFlowUseCase,
) : MavericksViewModel<EditUiState>(initialState) {

    init {
        viewModelScope.launch {
            getGraphColorsUseCase()
                ?.graphColors
                ?.map { TdsColor.valueOf(it.name) }
                ?.let { safeGraphColors ->
                    setState {
                        copy(
                            graphColors = safeGraphColors,
                        )
                    }
                }
        }

        getCurrentDateDailyFlowUseCase(initialState.currentDate)
            .catch {
                Log.e("EditViewModel", it.message.toString())
            }.setOnEach {
                copy(
                    dailyGraphData = it?.toFeatureModel() ?: DailyGraphData(),
                )
            }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<EditViewModel, EditUiState> {
        override fun create(state: EditUiState): EditViewModel
    }

    companion object :
        MavericksViewModelFactory<EditViewModel, EditUiState> by hiltMavericksViewModelFactory()
}
