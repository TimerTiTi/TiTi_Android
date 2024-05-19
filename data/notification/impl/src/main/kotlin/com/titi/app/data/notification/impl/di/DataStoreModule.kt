package com.titi.app.data.notification.impl.di

import android.content.Context
import com.titi.app.data.notification.impl.local.NotificationDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Provides
    @Singleton
    fun provideNotificationDataStore(@ApplicationContext context: Context) =
        NotificationDataStore(context)
}
