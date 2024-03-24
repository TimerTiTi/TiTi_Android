package com.titi.app.data.daily.impl.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.titi.app.data.daily.impl.local.model.TaskHistoryEntity

internal class MapListConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val mapType = Types
        .newParameterizedType(
            Map::class.java,
            String::class.java,
            Types.newParameterizedType(
                List::class.java,
                TaskHistoryEntity::class.java,
            ),
        )

    @TypeConverter
    fun fromJsonString(json: String): Map<String, List<TaskHistoryEntity>>? {
        val adapter = moshi.adapter<Map<String, List<TaskHistoryEntity>>>(mapType)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJsonString(data: Map<String, List<TaskHistoryEntity>>): String {
        val adapter = moshi.adapter<Map<String, List<TaskHistoryEntity>>>(mapType)
        return adapter.toJson(data)
    }
}
