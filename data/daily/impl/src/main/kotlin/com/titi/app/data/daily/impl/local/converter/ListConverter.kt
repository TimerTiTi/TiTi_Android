package com.titi.app.data.daily.impl.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class ListConverter {
    private val moshi = Moshi.Builder().build()
    private val intListType =
        Types.newParameterizedType(
            List::class.java,
            Long::class.javaObjectType,
        )

    @TypeConverter
    fun fromJsonString(value: String): List<Long>? {
        val adapter = moshi.adapter<List<Long>>(intListType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun toJsonString(list: List<Long>): String {
        val adapter = moshi.adapter<List<Long>>(intListType)
        return adapter.toJson(list)
    }
}
