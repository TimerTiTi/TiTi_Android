package com.titi.domain.time.mapper

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import com.titi.domain.time.model.RecordTimes
import org.threeten.bp.LocalDateTime

internal fun RecordTimesRepositoryModel.toDomainModel() = RecordTimes(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt?.let { LocalDateTime.parse(it) },
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask
)