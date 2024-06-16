package com.titi.app.data.daily.impl.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.titi.app.data.daily.impl.local.model.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DailyDao {

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)",
    )
    suspend fun getDateDaily(startDateTime: String, endDateTime: String): DailyEntity?

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)",
    )
    fun getDateDailyFlow(startDateTime: String, endDateTime: String): Flow<DailyEntity?>

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)",
    )
    suspend fun getWeekDaily(startDateTime: String, endDateTime: String): List<DailyEntity>?

    @Query("SELECT * FROM dailies ORDER BY id desc LIMIT 1")
    fun getLastDailyFlow(): Flow<DailyEntity?>

    @Query("SELECT * FROM dailies")
    suspend fun getAllDailies(): List<DailyEntity>?

    @Upsert
    suspend fun upsert(dailyEntity: DailyEntity)
}
