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
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime) LIMIT 1",
    )
    suspend fun getDateDaily(startDateTime: String, endDateTime: String): DailyEntity?

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime) LIMIT 1",
    )
    fun getDateDailyFlow(startDateTime: String, endDateTime: String): Flow<DailyEntity?>

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)",
    )
    suspend fun getDailies(startDateTime: String, endDateTime: String): List<DailyEntity>?

    @Query(
        "SELECT * FROM dailies " +
            " WHERE datetime(day) " +
            "BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)",
    )
    fun getDailiesFlow(startDateTime: String, endDateTime: String): Flow<List<DailyEntity>?>

    @Query("SELECT * FROM dailies")
    fun getAllDailiesFlow(): Flow<List<DailyEntity>?>

    @Upsert
    suspend fun upsert(dailyEntity: DailyEntity)
}
