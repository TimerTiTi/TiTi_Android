package com.titi.feature.time

import android.util.Log
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.domain.task.model.Task
import com.titi.domain.task.usecase.AddTaskUseCase
import com.titi.domain.task.usecase.GetTasksUseCase
import com.titi.domain.task.usecase.UpdateTaskNameUseCase
import com.titi.domain.task.usecase.UpdateTaskUseCase
import com.titi.domain.task.usecase.UpdateTasksPositionUseCase
import com.titi.domain.time.usecase.UpdateRecordTaskUseCase
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
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateRecordTaskUseCase: UpdateRecordTaskUseCase,
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

    fun updateRecordTask(taskName: String) {
        viewModelScope.launch {
            updateRecordTaskUseCase(taskName)
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