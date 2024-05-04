package com.titi.app.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.data.notification.api.NotificationRepository
import com.titi.app.domain.alarm.mapper.toRepositoryModel
import com.titi.app.domain.alarm.model.Alarm
import com.titi.app.domain.alarm.model.Alarms
import javax.inject.Inject
import org.threeten.bp.ZonedDateTime

class SetTimerAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(
        title: String,
        finishMessage: String,
        fiveMinutesBeforeFinish: String,
        measureTime: Long,
    ) {
        alarmRepository.cancelAlarms()

        val now = ZonedDateTime.now()
        val finishTime = now.plusSeconds(measureTime).toString()
        val fiveMinutesBeforeFinishTime: String? = if (measureTime > FIVE_MINUTES) {
            now.plusSeconds(measureTime - FIVE_MINUTES).toString()
        } else {
            null
        }

        val notification = notificationRepository.getNotification()
        val alarms = Alarms(
            alarms = mutableListOf<Alarm>().apply {
                if (notification.timerBeforeTheEnd) {
                    add(
                        Alarm(
                            title = title,
                            message = finishMessage,
                            finishTime = finishTime,
                        ),
                    )
                }

                if (
                    fiveMinutesBeforeFinishTime != null &&
                    notification.timerFiveMinutesBeforeTheEnd
                ) {
                    add(
                        Alarm(
                            title = title,
                            message = fiveMinutesBeforeFinish,
                            finishTime = fiveMinutesBeforeFinishTime,
                        ),
                    )
                }
            }.toList(),
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
