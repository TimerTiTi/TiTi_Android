package com.titi.app.data.notification.impl.repository

import com.titi.app.data.notification.api.NotificationRepository
import com.titi.app.data.notification.api.model.NotificationRepositoryModel
import com.titi.app.data.notification.impl.local.NotificationDataStore
import com.titi.app.data.notification.impl.mapper.toLocalModel
import com.titi.app.data.notification.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataStore: NotificationDataStore,
) : NotificationRepository {
    override suspend fun setNotification(notificationRepositoryModel: NotificationRepositoryModel) {
        notificationDataStore.setNotification(notificationRepositoryModel.toLocalModel())
    }

    override fun getNotificationFlow(): Flow<NotificationRepositoryModel> =
        notificationDataStore.getNotificationFlow()
            .map { it?.toRepositoryModel() ?: NotificationRepositoryModel() }
}
