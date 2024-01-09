package com.titi.app.doamin.daily.mapper

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.model.TaskHistory

internal fun Daily.toRepositoryModel() = DailyRepositoryModel(
    id = id,
    status = status,
    day = day,
    timeline = timeLine,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { it.value.map { it.toRepositoryModel() } }
)

internal fun TaskHistory.toRepositoryModel() = TaskHistoryRepositoryModel(
    startDate = startDate,
    endDate = endDate
)