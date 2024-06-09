package com.titi.app.feature.edit.model

import java.time.LocalDateTime

sealed interface EditActions {

    sealed interface Navigates : EditActions {
        data object Back : Navigates
    }

    sealed interface Updates : EditActions {
        data object Save : Updates

        data class ClickTaskName(
            val taskName: String,
            val index: Int,
        ) : Updates

        data class UpdateTaskName(
            val currentTaskName: String,
            val updateTaskName: String,
        ) : Updates

        data class UpsertTaskHistory(
            val taskName: String,
            val startDateTime: LocalDateTime,
            val endDateTime: LocalDateTime,
        ) : Updates

        data object Done : Updates
    }
}
