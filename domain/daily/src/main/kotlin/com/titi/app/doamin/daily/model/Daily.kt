package com.titi.app.doamin.daily.model

import android.os.Parcelable
import com.titi.app.core.util.addTimeLine
import kotlin.math.max
import kotlinx.parcelize.Parcelize
import org.threeten.bp.Duration
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

@Parcelize
data class Daily(
    val id: Long = 0,
    val status: String? = null,
    val day: String = ZonedDateTime.now(ZoneOffset.UTC).toString(),
    val timeLine: List<Long> = LongArray(24) { 0 }.toList(),
    val maxTime: Long = 0,
    val tasks: Map<String, Long>? = null,
    val taskHistories: Map<String, List<TaskHistory>>? = null,
) : Parcelable

@Parcelize
data class TaskHistory(
    val startDate: String,
    val endDate: String,
) : Parcelable

fun Daily.toUpdateDaily(taskHistoriesMap: Map<String, List<TaskHistory>>): Daily {
    var timeLine = LongArray(24) { 0 }.toList()
    var maxTime = 0L
    val tasks = mutableMapOf<String, Long>()

    taskHistoriesMap.forEach { (taskName, taskHistories) ->
        taskHistories.forEach { taskHistory ->
            val startZoneDateTime = ZonedDateTime.parse(taskHistory.startDate)
            val endZoneDateTime = ZonedDateTime.parse(taskHistory.endDate)

            val diffTime = Duration.between(startZoneDateTime, endZoneDateTime).seconds

            timeLine = addTimeLine(
                startTime = startZoneDateTime,
                endTime = endZoneDateTime,
                timeLine = timeLine,
            )
            maxTime = max(maxTime, diffTime)
            tasks[taskName] = tasks[taskName]?.plus(diffTime) ?: diffTime
        }
    }

    return this.copy(
        timeLine = timeLine,
        maxTime = maxTime,
        tasks = tasks,
        taskHistories = taskHistoriesMap,
    )
}
