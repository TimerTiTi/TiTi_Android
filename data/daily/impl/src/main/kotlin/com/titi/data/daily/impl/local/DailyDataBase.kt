package com.titi.data.daily.impl.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.titi.data.daily.impl.local.dao.DailyDao
import com.titi.data.daily.impl.local.model.DailyEntity

@Database(
    entities = [DailyEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class DailyDataBase : RoomDatabase() {

    abstract fun getDailyDao() : DailyDao

    companion object{
        const val DAILY_DATABASE_NAME = "titiDailyDataBaseName"
    }

}