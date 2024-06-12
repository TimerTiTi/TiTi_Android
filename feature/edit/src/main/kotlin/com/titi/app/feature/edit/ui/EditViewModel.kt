package com.titi.app.feature.edit.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.model.TaskHistory
import com.titi.app.doamin.daily.model.toUpdateDaily
import com.titi.app.doamin.daily.usecase.GetCurrentDateDailyFlowUseCase
import com.titi.app.domain.color.usecase.GetGraphColorsUseCase
import com.titi.app.feature.edit.model.DateTimeTaskHistory
import com.titi.app.feature.edit.model.EditActions
import com.titi.app.feature.edit.model.EditUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.time.ZoneId
import java.time.ZoneOffset
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
                    currentDaily = it ?: Daily(),
                )
            }
    }

    fun handleEditActions(editActions: EditActions.Updates) {
        when (editActions) {
            EditActions.Updates.Save -> {
            }

            is EditActions.Updates.ClickTaskName -> updateClickTaskName(
                taskName = editActions.taskName,
                index = editActions.index,
            )

            EditActions.Updates.Done -> updateClickTaskName(
                taskName = null,
                index = -1,
            )

            is EditActions.Updates.UpdateTaskName -> updateTaskName(
                currentTaskName = editActions.currentTaskName,
                updateTaskName = editActions.updateTaskName,
            )

            is EditActions.Updates.UpsertTaskHistory -> updateTaskHistory(
                taskName = editActions.taskName,
                currentTaskHistory = editActions.currentTaskHistory,
                updateTaskHistory = editActions.updateTaskHistory,
            )
        }
    }

    private fun updateClickTaskName(taskName: String?, index: Int) {
        setState {
            copy(
                clickedTaskName = taskName,
                selectedTaskIndex = index,
            )
        }
    }

    private fun updateTaskName(currentTaskName: String, updateTaskName: String) {
        withState {
            val taskHistories = it.currentDaily.taskHistories?.toMutableMap() ?: mutableMapOf()
            val tasks = it.currentDaily.tasks?.toMutableMap() ?: mutableMapOf()

            taskHistories[updateTaskName] = taskHistories[currentTaskName] ?: emptyList()
            taskHistories.remove(currentTaskName)
            tasks[updateTaskName] = tasks[currentTaskName] ?: 0L
            tasks.remove(currentTaskName)

            setState {
                copy(
                    clickedTaskName = updateTaskName,
                    currentDaily = currentDaily.copy(
                        taskHistories = taskHistories.toMap(),
                        tasks = tasks.toMap(),
                    ),
                )
            }
        }
    }

    private fun updateTaskHistory(
        taskName: String,
        currentTaskHistory: DateTimeTaskHistory?,
        updateTaskHistory: DateTimeTaskHistory,
    ) {
        withState {
            val taskHistories = it.currentDaily.taskHistories?.toMutableMap() ?: mutableMapOf()
            val removeTaskHistory = currentTaskHistory?.let { safeTaskHistory ->
                TaskHistory(
                    startDate = safeTaskHistory
                        .startDateTime
                        .atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .toString(),
                    endDate = safeTaskHistory
                        .endDateTime
                        .atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .toString(),
                )
            }
            val addTaskHistory = TaskHistory(
                startDate = updateTaskHistory
                    .startDateTime
                    .atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toString(),
                endDate = updateTaskHistory.endDateTime
                    .atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toString(),
            )

            taskHistories[taskName] = taskHistories[taskName]
                ?.toMutableList()
                ?.apply {
                    removeTaskHistory?.let { safeTaskHistory -> remove(safeTaskHistory) }
                    add(addTaskHistory)
                }
                ?: listOf(addTaskHistory)

            setState {
                copy(currentDaily = currentDaily.toUpdateDaily(taskHistories.toMap()))
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<EditViewModel, EditUiState> {
        override fun create(state: EditUiState): EditViewModel
    }

    companion object :
        MavericksViewModelFactory<EditViewModel, EditUiState> by hiltMavericksViewModelFactory()
}
