package com.titi.app.domain.alarm.usecase

import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.data.notification.api.NotificationRepository
import com.titi.app.domain.alarm.mapper.toRepositoryModel
import com.titi.app.domain.alarm.model.Alarm
import com.titi.app.domain.alarm.model.Alarms
import javax.inject.Inject
import org.threeten.bp.ZonedDateTime

class SetStopWatchAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(title: String, finishMessage: String, measureTime: Long) {
        alarmRepository.cancelAlarms()

        val now = ZonedDateTime.now()
        val finishTimeRange = (measureTime / ONE_HOUR_SECONDS) + 1..TWENTY_FOUR_HOURS
        val alarms = Alarms(
            alarms = finishTimeRange.map {
                Alarm(
                    title = title,
                    message = "$it$finishMessage",
                    finishTime = now.plusSeconds(
                        it * ONE_HOUR_SECONDS - measureTime,
                    ).toString(),
                )
            },
        )

        val notification = notificationRepository.getNotification()

        if (notification.stopwatch) {
            if (alarmRepository.canScheduleExactAlarms()) {
                alarmRepository.setExactAlarms(alarms.toRepositoryModel())
            } else {
                alarmRepository.addExactAlarms(alarms.toRepositoryModel())
            }
        }
    }

    companion object {
        private const val ONE_HOUR_SECONDS = 3600
        private const val TWENTY_FOUR_HOURS = 24
    }
}
