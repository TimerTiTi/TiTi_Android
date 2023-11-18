package com.titi.data.daily.impl.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class MapConverter {

    private val moshi = Moshi.Builder().build()
    private val mapType = Types
        .newParameterizedType(
            Map::class.java,
            String::class.java,
            Integer::class.java
        )

    @TypeConverter
    fun fromJsonString(json: String): Map<String, Int>? {
        val adapter = moshi.adapter<Map<String, Int>>(mapType)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun toJsonString(data: Map<String, Int>): String {
        val adapter = moshi.adapter<Map<String, Int>>(mapType)
        return adapter.toJson(data)
    }

}