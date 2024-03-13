package com.titi.app.feature.log.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.data.graph.api.GraphRepository
import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.doamin.daily.usecase.GetAllDailiesTasksUseCase
import com.titi.app.doamin.daily.usecase.GetCurrentDateDailyUseCase
import com.titi.app.doamin.daily.usecase.GetMonthDailyUseCase
import com.titi.app.doamin.daily.usecase.GetWeekDailyUseCase
import com.titi.app.doamin.daily.usecase.HasDailyForCurrentMonthUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsUseCase
import com.titi.app.domain.color.usecase.UpdateGraphColorsUseCase
import com.titi.app.feature.log.mapper.toDomainModel
import com.titi.app.feature.log.mapper.toFeatureModel
import com.titi.app.feature.log.mapper.toHomeFeatureModel
import com.titi.app.feature.log.mapper.toWeekFeatureModel
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.HomeUiState
import com.titi.app.feature.log.model.LogUiState
import com.titi.app.feature.log.model.WeekGraphData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.time.LocalDate
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
    getGraphColorsUseCase: GetGraphColorsUseCase,
    private val updateGraphColorsUseCase: UpdateGraphColorsUseCase,
    private val getAllDailiesTasksUseCase: GetAllDailiesTasksUseCase,
    private val getMonthDailyUseCase: GetMonthDailyUseCase,
    private val getCurrentDateDailyUseCase: GetCurrentDateDailyUseCase,
    private val getWeekDailyUseCase: GetWeekDailyUseCase,
    private val hasDailyForCurrentMonthUseCase: HasDailyForCurrentMonthUseCase,
    private val graphRepository: GraphRepository,
) : MavericksViewModel<LogUiState>(initialState) {

    init {
        getGraphColorsUseCase().catch {
            Log.e("LogViewModel", it.message.toString())
        }.filterNotNull()
            .setOnEach {
                copy(graphColorUiState = it.toFeatureModel())
            }

        graphRepository.getGraphCheckedFlow().catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach {
            copy(dailyUiState = dailyUiState.copy(checkedButtonStates = it.checkedButtonStates))
        }

        graphRepository.getGraphGoalTimeFlow().catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach {
            copy(graphGoalTimeUiState = it.toFeatureModel())
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

    fun updateCurrentDateHome(date: LocalDate) {
        val totalAsync = viewModelScope.async {
            getAllDailiesTasksUseCase()
        }

        val graphData = viewModelScope.async {
            getMonthDailyUseCase(date)
        }

        viewModelScope.launch {
            runCatching {
                Pair(totalAsync.await(), graphData.await()).toHomeFeatureModel(date)
            }.onSuccess {
                setState {
                    copy(
                        homeUiState = it,
                    )
                }
            }.onFailure {
                setState {
                    copy(
                        homeUiState = HomeUiState(),
                    )
                }
            }
        }
    }

    fun updateCurrentDateWeek(date: LocalDate) {
        viewModelScope.launch {
            getWeekDailyUseCase(date)
                .onSuccess {
                    setState {
                        copy(
                            weekUiState = weekUiState.copy(
                                currentDate = date,
                                weekGraphData = it?.toWeekFeatureModel(date) ?: WeekGraphData(),
                            ),
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(
                            weekUiState = weekUiState.copy(
                                currentDate = date,
                                weekGraphData = WeekGraphData(),
                            ),
                        )
                    }
                }
        }
    }

    fun updateHasDailyAtDailyTab(date: LocalDate) {
        viewModelScope.launch {
            val state = awaitState()
            if (
                state.weekUiState.currentDate.month == date.month &&
                state.weekUiState.hasDailies.isNotEmpty()
            ) {
                setState {
                    copy(
                        dailyUiState = dailyUiState.copy(
                            hasDailies = state.weekUiState.hasDailies,
                        ),
                    )
                }
            } else {
                val hasDailies = hasDailyForCurrentMonthUseCase(date)
                setState {
                    copy(
                        dailyUiState = dailyUiState.copy(
                            hasDailies = hasDailies,
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

    fun updateHasDailyAtWeekTab(date: LocalDate) {
        viewModelScope.launch {
            val state = awaitState()
            if (
                state.dailyUiState.currentDate.month == date.month &&
                state.dailyUiState.hasDailies.isNotEmpty()
            ) {
                setState {
                    copy(
                        weekUiState = weekUiState.copy(
                            hasDailies = state.dailyUiState.hasDailies,
                        ),
                    )
                }
            } else {
                val hasDailies = hasDailyForCurrentMonthUseCase(date)
                setState {
                    copy(
                        weekUiState = weekUiState.copy(
                            hasDailies = hasDailies,
                        ),
                    )
                }
            }
        }
    }

    fun updateCheckedState(page: Int, checked: Boolean, checkedButtonStates: List<Boolean>) {
        viewModelScope.launch {
            val updateCheckedButtonStates = checkedButtonStates.toMutableList().apply {
                set(page, checked)
            }

            graphRepository.setGraphChecked(
                GraphCheckedRepositoryModel(
                    updateCheckedButtonStates,
                ),
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
