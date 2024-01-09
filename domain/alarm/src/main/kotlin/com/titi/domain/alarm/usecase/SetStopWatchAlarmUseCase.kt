package com.titi.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.domain.alarm.mapper.toRepositoryModel
import com.titi.domain.alarm.model.Alarm
import com.titi.domain.alarm.model.Alarms
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class SetStopWatchAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke(
        title: String,
        finishMessage: String,
        measureTime: Long
    ) {
        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val finishTimeRange = (measureTime / ONE_HOUR_SECONDS) + 1..TWENTY_FOUR_HOURS
        val alarms = Alarms(
            alarms = finishTimeRange.map {
                Alarm(
                    title = title,
                    message = "$it$finishMessage",
                    finishTime = now.plusSeconds(it * ONE_HOUR_SECONDS - measureTime).toString()
                )
            }
        )

        if (alarmRepository.canScheduleExactAlarms()) {
            alarmRepository.setExactAlarms(alarms.toRepositoryModel())
        } else {
            alarmRepository.addExactAlarms(alarms.toRepositoryModel())
        }
    }

    companion object {
        private const val ONE_HOUR_SECONDS = 3600
        private const val TWENTY_FOUR_HOURS = 24
    }

}