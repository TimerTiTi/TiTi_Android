package com.titi.app.data.daily.impl.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class MapConverter {
    private val moshi = Moshi.Builder().build()
    private val mapType = Types
        .newParameterizedType(
            Map::class.java,
            String::class.java,
            Long::class.javaObjectType,
        )

    @TypeConverter
    fun fromJsonString(json: String): Map<String, Long>? {
        val adapter = moshi.adapter<Map<String, Long>>(mapType)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJsonString(data: Map<String, Long>): String {
        val adapter = moshi.adapter<Map<String, Long>>(mapType)
        return adapter.toJson(data)
    }
}
