package com.titi.doamin.daily.mapper

import com.titi.data.daily.api.model.DailyRepositoryModel
import com.titi.data.daily.api.model.TaskHistoryRepositoryModel
import com.titi.doamin.daily.model.Daily
import com.titi.doamin.daily.model.TaskHistory
import org.threeten.bp.ZonedDateTime

internal fun DailyRepositoryModel.toDomain() = Daily(
    id = id,
    status = status,
    day = ZonedDateTime.parse(day),
    timeLine = timeline,
    maxTime = maxTime,
    tasks = tasks,
    taskHistories = taskHistories?.mapValues { it.value.map { it.toDomain() } }
)

internal fun TaskHistoryRepositoryModel.toDomain() = TaskHistory(
    startDate = ZonedDateTime.parse(startDate),
    endDate = ZonedDateTime.parse(endDate)
)