package com.titi.domain.time.mapper

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import com.titi.domain.time.model.RecordTimes
import org.threeten.bp.LocalDateTime

internal fun RecordTimesRepositoryModel.toDomainModel() = RecordTimes(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = if (recordStartAt.isNullOrBlank()) {
        null
    } else {
        LocalDateTime.parse(recordStartAt)
    },
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask
)