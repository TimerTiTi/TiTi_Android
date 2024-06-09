package com.titi.app.feature.edit.util

import com.titi.app.feature.edit.model.TaskHistory
import java.time.LocalDateTime

fun isStartTimeTaskHistoryOverlap(
    startDateTime: LocalDateTime,
    taskHistories: List<TaskHistory>,
): Boolean {
    for (taskHistory in taskHistories) {
        if (
            startDateTime >= taskHistory.startDateTime &&
            startDateTime <= taskHistory.endDateTime
        ) {
            return true
        }
    }
    return false
}

fun isTaskHistoryOverlap(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    taskHistories: List<TaskHistory>,
): Boolean {
    for (taskHistory in taskHistories) {
        if (startDateTime <= taskHistory.endDateTime && endDateTime >= taskHistory.startDateTime) {
            return true
        }
    }
    return false
}
