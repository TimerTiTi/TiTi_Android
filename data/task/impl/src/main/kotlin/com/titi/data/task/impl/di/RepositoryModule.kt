package com.titi.data.task.impl.di

import com.titi.data.task.api.TaskRepository
import com.titi.data.task.impl.repository.TaskRepositoryImpl
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