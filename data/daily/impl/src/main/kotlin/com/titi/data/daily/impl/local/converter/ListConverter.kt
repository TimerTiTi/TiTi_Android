package com.titi.data.daily.impl.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class ListConverter {

    private val moshi = Moshi.Builder().build()
    private val intListType = Types.newParameterizedType(List::class.java, Int::class.java)

    @TypeConverter
    fun fromJsonString(value: String): List<Int>? {
        val adapter = moshi.adapter<List<Int>>(intListType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun toJsonString(list: List<Int>): String {
        val adapter = moshi.adapter<List<Int>>(intListType)
        return adapter.toJson(list)
    }

}