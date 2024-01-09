package com.titi.doamin.daily.mapper

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.doamin.daily.model.Daily
import com.titi.doamin.daily.model.TaskHistory

internal fun Daily.toRepositoryModel() = DailyRepositoryModel(
    id = id,
    status = status,
    day = day.toString(),
    timeline = timeLine,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { it.value.map { it.toRepositoryModel() } }
)

internal fun TaskHistory.toRepositoryModel() = TaskHistoryRepositoryModel(
    startDate = startDate.toString(),
    endDate = endDate.toString()
)