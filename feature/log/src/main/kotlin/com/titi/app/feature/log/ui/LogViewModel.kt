package com.titi.app.feature.log.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.doamin.daily.usecase.GetCurrentDateDailyUseCase
import com.titi.app.doamin.daily.usecase.GetWeekDailyUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsUseCase
import com.titi.app.domain.color.usecase.UpdateGraphColorsUseCase
import com.titi.app.feature.log.mapper.toDomainModel
import com.titi.app.feature.log.mapper.toFeatureModel
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.LogUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.LocalDate

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
    getGraphColorsUseCase: GetGraphColorsUseCase,
    private val updateGraphColorsUseCase: UpdateGraphColorsUseCase,
    private val getCurrentDateDailyUseCase: GetCurrentDateDailyUseCase,
    private val getWeekDailyUseCase: GetWeekDailyUseCase,
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

    fun updateWeekCurrentDate(date: LocalDate) {
        viewModelScope.launch {
            getWeekDailyUseCase(date)
                .onSuccess {
                    setState {
                        copy(
                            weekUiState = weekUiState.copy(
                                currentDate = date,
                            ),
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(
                            weekUiState = weekUiState.copy(
                                currentDate = date,
                            ),
                        )
                    }
                }
        }
    }

    fun updateCurrentDateDaily(date: LocalDate) {
        viewModelScope.launch {
            getCurrentDateDailyUseCase(date)
                .onSuccess {
                    setState {
                        copy(
                            dailyUiState = dailyUiState.copy(
                                currentDate = date,
                                dailyGraphData = it?.toFeatureModel() ?: DailyGraphData(),
                            ),
                        )
                    }
                }.onFailure {
                    setState {
                        copy(
                            dailyUiState = dailyUiState.copy(
                                currentDate = date,
                                dailyGraphData = DailyGraphData(),
                            ),
                        )
                    }
                }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<LogViewModel, LogUiState> {
        override fun create(state: LogUiState): LogViewModel
    }

    companion object :
        MavericksViewModelFactory<LogViewModel, LogUiState> by hiltMavericksViewModelFactory()
}
