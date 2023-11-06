package com.titi.data.daily.impl.mapper

import com.titi.data.daily.api.model.DailyRepositoryModel
import com.titi.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.data.daily.impl.local.model.DailyEntity
import com.titi.data.daily.impl.local.model.TaskHistoryEntity

internal fun DailyEntity.toRepository() = DailyRepositoryModel(
    id = id,
    status = status,
    day = day,
    timeline = timeline,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { taskHistoryMap ->
        taskHistoryMap.value.map { taskHistory ->
            taskHistory.toRepository()
        }
    }

)

internal fun TaskHistoryEntity.toRepository() = TaskHistoryRepositoryModel(
    startDate = startDate,
    endDate = endDate
)