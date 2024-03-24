package com.titi.app.feature.time.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.domain.task.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
) : MavericksState
