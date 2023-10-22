package com.titi.data.task.impl.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.titi.data.task.impl.local.dao.TaskDao
import com.titi.data.task.impl.local.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        const val TASK_DATABASE_NAME = "titiTaskDataBaseName"
    }

}