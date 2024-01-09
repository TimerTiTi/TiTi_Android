package com.titi.app.data.daily.impl.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.titi.app.data.daily.impl.local.converter.ListConverter
import com.titi.app.data.daily.impl.local.converter.MapConverter
import com.titi.app.data.daily.impl.local.converter.MapListConverter
import com.titi.app.data.daily.impl.local.dao.DailyDao
import com.titi.app.data.daily.impl.local.model.DailyEntity

@Database(
    entities = [DailyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListConverter::class,
    MapConverter::class,
    MapListConverter::class
)
internal abstract class DailyDataBase : RoomDatabase() {

    abstract fun getDailyDao() : DailyDao

    companion object{
        const val DAILY_DATABASE_NAME = "titiDailyDataBaseName"
    }

}