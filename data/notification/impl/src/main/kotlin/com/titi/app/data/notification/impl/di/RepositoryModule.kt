package com.titi.app.data.notification.impl.di

import com.titi.app.data.notification.api.NotificationRepository
import com.titi.app.data.notification.impl.repository.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun provideNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl,
    ): NotificationRepository
}
