package com.titi.app.feature.log.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.extension.makeDefaultWeekLineChardData
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.domain.color.model.GraphColor
import java.time.LocalDate

data class LogUiState(
    val graphColors: GraphColorUiState = GraphColorUiState(),
    val homeUiState: HomeUiState = HomeUiState(),
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

data class HomeUiState(
    val homeGraphData: HomeGraphData = HomeGraphData(),
) {
    data class HomeGraphData(
        val homeMonthPieData: HomeMonthPieData = HomeMonthPieData(),
        val homeMonthGraphData: HomeMonthGraphData = HomeMonthGraphData(),
        val homeWeekPieData: HomeWeekPieData = HomeWeekPieData(),
        val homeWeekGraphData: HomeWeekGraphData = HomeWeekGraphData(),
        val homeDailyGraphData: HomeDailyGraphData = HomeDailyGraphData(),
    )

    data class HomeMonthPieData(
        val totalTimeSeconds: Long = 0,
        val defaultTimeSeconds: Long = 360000,
    )

    data class HomeMonthGraphData(
        val totalTimeSeconds: Long = 0,
        val taskData: List<TdsTaskData> = emptyList(),
    )

    data class HomeWeekPieData(
        val totalTimeSeconds: Long = 0,
        val defaultTimeSeconds: Long = 90000,
    )

    data class HomeWeekGraphData(
        val weekInformation: Triple<String, String, String> = LocalDate.now().getWeekInformation(),
        val totalWeekTime: String = "",
        val averageWeekTime: String = "",
        val weekLineChartData: List<TdsWeekLineChartData> =
            LocalDate.now().makeDefaultWeekLineChardData(),
    )

    data class HomeDailyGraphData(
        val currentDate: LocalDate = LocalDate.now(),
        val timeLines: List<Long> = LongArray(24) { 0L }.toList(),
    ) {
        val todayDate = currentDate.toString().replace('-', '.')
        val todayDayOfTheWeek = currentDate.dayOfWeek.value
    }
}

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
    val averageWeekTime: String = "",
    val weekLineChartData: List<TdsWeekLineChartData> =
        LocalDate.now().makeDefaultWeekLineChardData(),
    val topLevelTaskTotal: String = "",
    val topLevelTdsTaskData: List<TdsTaskData> = emptyList(),
)
