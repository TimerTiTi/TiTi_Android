package com.titi.feature.time.ui.task

import android.util.Log
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.domain.task.model.Task
import com.titi.app.domain.task.usecase.AddTaskUseCase
import com.titi.app.domain.task.usecase.GetTasksUseCase
import com.titi.app.domain.task.usecase.UpdateTaskNameUseCase
import com.titi.app.domain.task.usecase.UpdateTaskUseCase
import com.titi.app.domain.task.usecase.UpdateTasksPositionUseCase
import com.titi.domain.time.model.CurrentTask
import com.titi.domain.time.usecase.UpdateCurrentTaskUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class TaskUiState(
    val tasks: List<Task> = emptyList()
) : MavericksState

class TaskViewModel @AssistedInject constructor(
    @Assisted initialState: TaskUiState,
    getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateCurrentTaskUseCase: UpdateCurrentTaskUseCase,
    private val updateTaskNameUseCase: UpdateTaskNameUseCase,
    private val updateTasksPositionUseCase: UpdateTasksPositionUseCase,
) : MavericksViewModel<TaskUiState>(initialState) {

    init {
        getTasksUseCase()
            .catch {
                Log.e("TaskViewModel", it.message.toString())
            }.setOnEach {
                copy(tasks = it)
            }
    }

    fun addTask(taskName: String) {
        viewModelScope.launch {
            addTaskUseCase(taskName)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

    fun updateRecordTask(task: Task) {
        viewModelScope.launch {
            updateCurrentTaskUseCase(
                CurrentTask(
                    taskName = task.taskName,
                    isTaskTargetTimeOn = task.isTaskTargetTimeOn,
                    taskTargetTime = task.taskTargetTime
                )
            )
        }
    }

    fun updateTaskName(task: Task, updateTaskName: String) {
        viewModelScope.launch {
            updateTaskNameUseCase(task, updateTaskName)
        }
    }

    fun moveTask(fromTask: Task, toTask: Task) {
        viewModelScope.launch {
            updateTasksPositionUseCase(
                fromTask = fromTask,
                toTask = toTask
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TaskViewModel, TaskUiState> {
        override fun create(state: TaskUiState): TaskViewModel
    }

    companion object :
        MavericksViewModelFactory<TaskViewModel, TaskUiState> by hiltMavericksViewModelFactory()

}