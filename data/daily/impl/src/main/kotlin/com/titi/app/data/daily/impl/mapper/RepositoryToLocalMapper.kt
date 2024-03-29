package com.titi.app.data.daily.impl.mapper

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.app.data.daily.impl.local.model.DailyEntity
import com.titi.app.data.daily.impl.local.model.TaskHistoryEntity

internal fun DailyRepositoryModel.toLocalModel() = DailyEntity(
    id = id,
    status = status,
    day = day,
    timeline = timeline,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { taskRepositoryMap ->
        taskRepositoryMap.value.map { taskHistoryRepository ->
            taskHistoryRepository.toLocalModel()
        }
    },
)

internal fun TaskHistoryRepositoryModel.toLocalModel() = TaskHistoryEntity(
    startDate = startDate,
    endDate = endDate,
)
