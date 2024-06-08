package com.titi.app.feature.edit.model

import java.time.LocalDateTime

sealed interface EditActions {

    sealed interface Navigates : EditActions {
        data object Back : Navigates
    }

    sealed interface Updates : EditActions {
        data object Save : Updates

        data class ClickTaskName(
            val taskName: String = "",
            val index: Int = -1,
        ) : Updates

        data class UpdateTaskName(
            val currentTaskName: String,
            val updateTaskName: String,
        ) : Updates

        data class UpdateTaskHistory(
            val taskName: String,
            val startDateTime: LocalDateTime,
            val endDateTime: LocalDateTime,
        ) : Updates

        data class AddTaskHistory(
            val taskName: String,
            val startDateTime: LocalDateTime,
            val endDateTime: LocalDateTime,
        )

        data object Done : Updates
    }
}
