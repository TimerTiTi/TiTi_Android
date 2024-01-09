package com.titi.app.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.domain.alarm.mapper.toRepositoryModel
import com.titi.app.domain.alarm.model.Alarm
import com.titi.app.domain.alarm.model.Alarms
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class SetTimerAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke(
        title: String,
        finishMessage: String,
        fiveMinutesBeforeFinish: String,
        measureTime: Long
    ) {
        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val finishTime = now.plusSeconds(measureTime).toString()
        val fiveMinutesBeforeFinishTime: String? = if (measureTime > FIVE_MINUTES) {
            now.plusSeconds(measureTime - FIVE_MINUTES).toString()
        } else null

        val alarms = Alarms(
            alarms = mutableListOf<Alarm>().apply {
                add(
                    Alarm(
                        title = title,
                        message = finishMessage,
                        finishTime = finishTime
                    )
                )

                if (fiveMinutesBeforeFinishTime != null) {
                    add(
                        Alarm(
                            title = title,
                            message = fiveMinutesBeforeFinish,
                            finishTime = fiveMinutesBeforeFinishTime
                        )
                    )
                }
            }.toList()
        )

        if (alarmRepository.canScheduleExactAlarms()) {
            alarmRepository.setExactAlarms(alarms.toRepositoryModel())
        } else {
            alarmRepository.addExactAlarms(alarms.toRepositoryModel())
        }
    }

    companion object {
        private const val FIVE_MINUTES = 300
    }

}