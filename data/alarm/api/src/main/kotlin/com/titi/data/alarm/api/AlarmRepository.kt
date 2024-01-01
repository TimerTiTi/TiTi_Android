package com.titi.data.alarm.api

import com.titi.data.alarm.api.model.AlarmsRepositoryModel

interface AlarmRepository {

    suspend fun getAlarms(): AlarmsRepositoryModel?

    fun canScheduleExactAlarms(): Boolean

    suspend fun setExactAlarms(alarms: AlarmsRepositoryModel)

    suspend fun cancelAlarms()

}