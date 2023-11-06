package com.titi.data.daily.impl.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.titi.data.daily.impl.local.model.DailyEntity

@Dao
internal interface DailyDao {

    @Query("SELECT * FROM dailies ORDER BY id DESC LIMIT 1")
    suspend fun getDaily() : DailyEntity?

    @Upsert
    suspend fun upsert(dailyEntity: DailyEntity)

}