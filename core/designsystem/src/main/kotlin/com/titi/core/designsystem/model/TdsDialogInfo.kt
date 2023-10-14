package com.titi.core.designsystem.model

sealed interface TdsDialogInfo {

    val title: String
    val message: String
    val cancelable: Boolean
    val onDismiss: (() -> Unit)?

    data class Confirm(
        override val title: String,
        override val message: String,
        override val cancelable: Boolean = false,
        override val onDismiss: (() -> Unit)? = null,
        val positiveText: String,
        val onPositive: () -> Unit,
        val negativeText: String,
        val onNegative: (() -> Unit)? = null
    ) : TdsDialogInfo

    data class Alert(
        override val title: String,
        override val message: String,
        override val cancelable: Boolean,
        override val onDismiss: (() -> Unit)?,
        val confirmText: String,
        val onConfirm: (() -> Unit)?
    ) : TdsDialogInfo

}