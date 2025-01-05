package com.titi.app.doamin.daily.mapper

import com.titi.app.core.util.removeSpecialCharacter
import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.model.TaskHistory

internal fun DailyRepositoryModel.toDomainModel() = Daily(
    id = id,
    status = status,
    day = day,
    timeLine = timeline,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { it.value.map { it.toDomainModel() } },
)

internal fun DailyRepositoryModel.toDomainModelWithRemovingSpecialCharacters() = Daily(
    id = id,
    status = status,
    day = day,
    timeLine = timeline,
    maxTime = maxTime,
    tasks = tasks?.mapKeys { it.key.removeSpecialCharacter() },
    taskHistories = taskHistories
        ?.mapKeys { it.key.removeSpecialCharacter() }
        ?.mapValues { it.value.map { it.toDomainModel() } },
)

internal fun TaskHistoryRepositoryModel.toDomainModel() = TaskHistory(
    startDate = startDate,
    endDate = endDate,
)
