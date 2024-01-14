package com.titi.app.data.daily.impl.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.titi.app.data.daily.impl.local.model.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DailyDao {
    @Query("SELECT * FROM dailies  ORDER BY id DESC LIMIT 1")
    suspend fun getCurrentDaily(): DailyEntity?

    @Query("SELECT * FROM dailies ORDER BY id DESC LIMIT 1")
    fun getCurrentDailyFlow(): Flow<DailyEntity?>

    @Upsert
    suspend fun upsert(dailyEntity: DailyEntity)
}
