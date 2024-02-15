package com.titi.app.feature.log.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
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
    val dailyGraphData: DailyGraphData = DailyGraphData(),
)

data class DailyGraphData(
    val totalTime: String = "",
    val maxTime: String = "",
    val timeLine: List<Long> = LongArray(24) { 0L }.toList(),
    val taskData: List<TdsTaskData> = emptyList(),
    val tdsTimeTableData: List<TdsTimeTableData> = emptyList(),
)

data class WeekUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val weekGraphData: WeekGraphData = WeekGraphData(),
)

data class WeekGraphData(
    val weekInformation: Triple<String, String, String> = LocalDate.now().getWeekInformation(),
    val totalWeekTime: String = "",
    val maxWeekTime: String = "",
    val weekLineChartData: List<TdsWeekLineChartData> = emptyList(),
    val topLevelTaskTotal: String = "",
    val topLevelTdsTaskData: List<TdsTaskData> = emptyList(),
)



