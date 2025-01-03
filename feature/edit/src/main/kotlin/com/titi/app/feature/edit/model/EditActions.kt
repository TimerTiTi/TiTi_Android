package com.titi.app.feature.edit.model

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
            val currentTaskHistory: DateTimeTaskHistory?,
            val updateTaskHistory: DateTimeTaskHistory,
        ) : Updates

        data object Done : Updates

        data class DeleteTaskHistory(
            val taskName: String,
            val taskHistory: DateTimeTaskHistory,
        ) : Updates
    }
}
