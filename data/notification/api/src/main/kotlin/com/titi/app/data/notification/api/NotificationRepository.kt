package com.titi.app.data.notification.api

import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun setNotification(notificationRepositoryModel: NotificationRepositoryModel)

    fun getNotificationFlow(): Flow<NotificationRepositoryModel>
}
