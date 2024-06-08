package com.titi.app.feature.edit.model

import android.os.Build
import android.os.Bundle
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

data class EditUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val graphColors: List<TdsColor> = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D10,
        TdsColor.D11,
        TdsColor.D12,
    ),
    val dailyGraphData: DailyGraphData = DailyGraphData(),
    val clickedTaskName: String? = null,
) : MavericksState {
    constructor(args: Bundle) : this(
        currentDate = LocalDate.parse(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                args.getParcelable(
                    Mavericks.KEY_ARG,
                    String::class.java,
                )
            } else {
                args.getParcelable(Mavericks.KEY_ARG)
            } ?: LocalDate.now().toString(),
        ),
    )
}

data class DailyGraphData(
    val totalTime: String = 0L.getTimeString(),
    val maxTime: String = 0L.getTimeString(),
    val timeLine: List<Long> = LongArray(24) { 0L }.toList(),
    val taskData: List<TdsTaskData> = emptyList(),
    val tdsTimeTableData: List<TdsTimeTableData> = emptyList(),
    val taskHistories: Map<String, List<TaskHistory>>? = null,
)

data class TaskHistory(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
) {
    val diffTime = Duration.between(startDateTime, endDateTime).toMillis() / 1000
}
