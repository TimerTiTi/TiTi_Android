package com.titi.app.data.notification.impl.mapper

import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import com.titi.app.data.notification.impl.local.model.NotificationEntity

internal fun NotificationRepositoryModel.toLocalModel() = NotificationEntity(
    timerFiveMinutesBeforeTheEnd = timerFiveMinutesBeforeTheEnd,
    timerBeforeTheEnd = timerBeforeTheEnd,
    stopwatch = stopwatch,
)
