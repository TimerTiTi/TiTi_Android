package com.titi.core.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

enum class MoshiHolder constructor(val moshi: Moshi) {

    MOSHI(Moshi.Builder().add(KotlinJsonAdapterFactory()).build());

    fun <T> fromJson(json: String, classOfT: Class<T>): T? = moshi.adapter(classOfT).fromJson(json)

    fun toJson(any: Any): String = any.toJson()

}

inline fun <reified T : Any> T.toJson(): String =
    MoshiHolder.MOSHI.moshi.adapter(T::class.java).toJson(this)

inline fun <reified T : Any> String.fromJson(): T? =
    MoshiHolder.MOSHI.moshi.adapter(T::class.java).fromJson(this)