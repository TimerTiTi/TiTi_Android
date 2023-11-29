package com.titi.data.daily.impl.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.titi.data.daily.impl.local.model.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DailyDao {

    @Query("SELECT * FROM dailies WHERE date(day) = date('now', 'localtime') ORDER BY id DESC LIMIT 1")
    suspend fun getCurrentDaily(): DailyEntity?

    @Query("SELECT * FROM dailies ORDER BY id DESC LIMIT 1")
    fun getCurrentDailyFlow(): Flow<DailyEntity?>

    @Upsert
    suspend fun upsert(dailyEntity: DailyEntity)

}