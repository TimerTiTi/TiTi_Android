package com.titi.app.data.task.impl.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.titi.app.data.task.impl.local.TaskDataBase
import com.titi.app.data.task.impl.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataBaseModule {

    @Singleton
    @Provides
    fun provideTaskDataBase(@ApplicationContext context: Context): TaskDataBase = databaseBuilder(
        context,
        TaskDataBase::class.java,
        TaskDataBase.TASK_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideTaskDao(taskDataBase: TaskDataBase): TaskDao =
        taskDataBase.getTaskDao()

}