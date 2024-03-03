package com.titi.app.data.daily.impl.mapper

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.app.data.daily.impl.local.model.DailyEntity
import com.titi.app.data.daily.impl.local.model.TaskHistoryEntity

internal fun DailyEntity.toRepositoryModel() = DailyRepositoryModel(
    id = id,
    status = status,
    day = day,
    timeline = timeline,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { taskHistoryMap ->
        taskHistoryMap.value.map { taskHistory ->
            taskHistory.toRepositoryModel()
        }
    },
)

internal fun TaskHistoryEntity.toRepositoryModel() = TaskHistoryRepositoryModel(
    startDate = startDate,
    endDate = endDate,
)
