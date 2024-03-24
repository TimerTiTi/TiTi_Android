package com.titi.app.data.task.impl.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.titi.app.data.task.impl.local.dao.TaskDao
import com.titi.app.data.task.impl.local.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false,
)
internal abstract class TaskDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        const val TASK_DATABASE_NAME = "titiTaskDataBaseName"
    }
}
