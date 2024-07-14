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
import com.titi.app.doamin.daily.usecase.GetCurrentWeekDailiesFlowUseCase
import com.titi.app.doamin.daily.usecase.HasDailyForCurrentMonthUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsFlowUseCase
import com.titi.app.domain.color.usecase.UpdateGraphColorsUseCase
import com.titi.app.feature.log.mapper.toDailyFeatureModel
import com.titi.app.feature.log.mapper.toDomainModel
import com.titi.app.feature.log.mapper.toFeatureModel
import com.titi.app.feature.log.mapper.toHomeFeatureModel
import com.titi.app.feature.log.mapper.toRepositoryModel
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class LogViewModel @AssistedInject constructor(
    @Assisted initialState: LogUiState,
    getGraphColorsFlowUseCase: GetGraphColorsFlowUseCase,
    getAllDailiesFlowUseCase: GetAllDailiesFlowUseCase,
    private val getCurrentDateDailyFlowUseCase: GetCurrentDateDailyFlowUseCase,
    private val getCurrentWeekDailiesFlowUseCase: GetCurrentWeekDailiesFlowUseCase,
    private val updateGraphColorsUseCase: UpdateGraphColorsUseCase,
    private val hasDailyForCurrentMonthUseCase: HasDailyForCurrentMonthUseCase,
    private val graphRepository: GraphRepository,
) : MavericksViewModel<LogUiState>(initialState) {

    private val currentDailyDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    private val currentWeekDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())

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

        currentDailyDate.flatMapLatest {
            getCurrentDateDailyFlowUseCase(it)
        }.catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach { daily ->
            copy(
                dailyUiState = dailyUiState.copy(
                    currentDate = currentDailyDate.value,
                    isCreate = daily == null,
                    dailyGraphData = daily
                        ?.toDailyFeatureModel(graphColorUiState.graphColors)
                        ?: DailyGraphData(),
                ),
            )
        }

        currentWeekDate.flatMapLatest {
            getCurrentWeekDailiesFlowUseCase(it)
        }.catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach { daily ->
            copy(
                weekUiState = weekUiState.copy(
                    currentDate = currentWeekDate.value,
                    isCreate = daily == null,
                    weekGraphData = daily
                        ?.toWeekFeatureModel(currentWeekDate.value)
                        ?: WeekGraphData(),
                ),
            )
        }

        graphRepository.getGraphGoalTimeFlow().catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach {
            copy(homeUiState = homeUiState.copy(graphGoalTime = it.toFeatureModel()))
        }

        graphRepository.getGraphCheckedFlow().catch {
            Log.e("LogViewModel", it.message.toString())
        }.setOnEach {
            copy(dailyUiState = dailyUiState.copy(checkedButtonStates = it.checkedButtonStates))
        }
    }

    fun updateTabSelectedIndex(index: Int) {
        setState {
            copy(
                tabSelectedIndex = index,
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
        graphGoalTime: HomeUiState.GraphGoalTime,
    ) {
        viewModelScope.launch {
            val updateGraphGoalTimeUiState = when {
                monthGoalTime != null && monthGoalTime > 0 -> graphGoalTime.copy(
                    monthGoalTime = monthGoalTime,
                )

                weekGoalTime != null && weekGoalTime > 0 -> graphGoalTime.copy(
                    weekGoalTime = weekGoalTime,
                )

                else -> graphGoalTime
            }

            graphRepository.setGraphGoalTime(updateGraphGoalTimeUiState.toRepositoryModel())
        }
    }

    fun updateCurrentDailyDate(date: LocalDate) {
        currentDailyDate.value = date
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

    fun updateCurrentWeekDate(date: LocalDate) {
        currentWeekDate.value = date
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

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<LogViewModel, LogUiState> {
        override fun create(state: LogUiState): LogViewModel
    }

    companion object :
        MavericksViewModelFactory<LogViewModel, LogUiState> by hiltMavericksViewModelFactory()
}
