package com.titi.app.feature.log.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.GraphColor
import java.time.LocalDate

data class LogUiState(
    val graphColors: GraphColorUiState = GraphColorUiState(),
    val dailyUiState: DailyUiState = DailyUiState(),
    val weekUiState: WeekUiState = WeekUiState(),
) : MavericksState

data class GraphColorUiState(
    val selectedIndex: Int = 0,
    val direction: GraphColor.GraphDirection = GraphColor.GraphDirection.Right,
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
)

data class DailyUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val daily: Daily? = null,
) {
    private val sumTime = daily?.tasks?.values?.sum()

    val timeLine = daily?.timeLine
    val totalTime = sumTime?.getTimeString()
    val maxTime = daily?.maxTime?.getTimeString()
    val taskData = daily?.tasks?.map {
        TdsTaskData(
            key = it.key,
            value = it.value.getTimeString(),
            progress = if (sumTime != null && sumTime > 0L) {
                it.value / sumTime.toFloat()
            } else {
                0f
            },
        )
    }
}

data class WeekUiState(
    val currentDate: LocalDate = LocalDate.now(),
)
