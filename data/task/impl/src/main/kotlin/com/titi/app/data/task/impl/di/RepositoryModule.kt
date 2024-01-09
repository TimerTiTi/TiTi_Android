package com.titi.app.data.task.impl.di

import com.titi.app.data.task.api.TaskRepository
import com.titi.app.data.task.impl.repository.TaskRepositoryImpl
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
    fun provideTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

}