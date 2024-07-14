package com.titi.app.feature.log.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.data.graph.api.GraphRepository
import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.doamin.daily.usecase.GetAllDailiesFlowUseCase
import com.titi.app.doamin.daily.usecase.GetCurrentDateDailyFlowUseCase
import com.titi.app.doamin.daily.usecase.GetWeekDailyUseCase
import com.titi.app.doamin.daily.usecase.HasDailyForCurrentMonthUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsFlowUseCase
import com.titi.app.domain.color.usecase.UpdateGraphColorsUseCase
import com.titi.app.feature.log.mapper.toDomainModel
import com.titi.app.feature.log.mapper.toFeatureModel
import com.titi.app.feature.log.mapper.toHomeFeatureModel
import com.titi.app.feature.log.mapper.toRepositoryModel
import com.titi.app.feature.log.mapper.toWeekFeatureModel
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.GraphGoalTimeUiState
import com.titi.app.feature.log.model.LogUiState
import com.titi.app.feature.log.model.WeekGraphData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
    getGraphColorsFlowUseCase: GetGraphColorsFlowUseCase,
    getAllDailiesFlowUseCase: GetAllDailiesFlowUseCase,
    private val updateGraphColorsUseCase: UpdateGraphColorsUseCase,
    private val getCurrentDateDailyFlowUseCase: GetCurrentDateDailyFlowUseCase,
    private val getWeekDailyUseCase: GetWeekDailyUseCase,
    private val hasDailyForCurrentMonthUseCase: HasDailyForCurrentMonthUseCase,
    private val graphRepository: GraphRepository,
) : MavericksViewModel<LogUiState>(initialState) {

    private val currentDailyDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())

    init {
        getGraphColorsFlowUseCase().catch {
            Log.e("LogViewModel", it.message.toString())
        }.filterNotNull()
            .setOnEach {
                copy(graphColorUiState = it.toFeatureModel())
            }

        getAllDailiesFlowUseCase().catch {
            Log.e("LogViewModel", it.message.toString())
        }.filterNotNull()
            .setOnEach { dailies ->
                copy(
                    homeUiState = dailies.toHomeFeatureModel(),
                )
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

        currentDailyDate.flatMapLatest {
            getCurrentDateDailyFlowUseCase(it)
        }.catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach { daily ->
            copy(
                dailyUiState = dailyUiState.copy(
                    currentDate = currentDailyDate.value,
                    dailyGraphData = daily
                        ?.toFeatureModel(graphColorUiState.graphColors)
                        ?: DailyGraphData(),
                ),
            )
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

    fun updateGraphGoalTime(
        monthGoalTime: Int? = null,
        weekGoalTime: Int? = null,
        graphGoalTimeUiState: GraphGoalTimeUiState,
    ) {
        viewModelScope.launch {
            val updateGraphGoalTimeUiState = when {
                monthGoalTime != null && monthGoalTime > 0 -> graphGoalTimeUiState.copy(
                    monthGoalTime = monthGoalTime,
                )

                weekGoalTime != null && weekGoalTime > 0 -> graphGoalTimeUiState.copy(
                    weekGoalTime = weekGoalTime,
                )

                else -> graphGoalTimeUiState
            }

            graphRepository.setGraphGoalTime(updateGraphGoalTimeUiState.toRepositoryModel())
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

    fun updateCurrentDate(date: LocalDate) {
        currentDailyDate.value = date
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

    fun updateTabSelectedIndex(index: Int) {
        setState {
            copy(
                tabSelectedIndex = index,
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
