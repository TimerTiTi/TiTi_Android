package com.titi.app.domain.time.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentTask(
    val taskName: String,
    val taskTargetTime: Long = 3600,
    val isTaskTargetTimeOn: Boolean = false
) : Parcelable
