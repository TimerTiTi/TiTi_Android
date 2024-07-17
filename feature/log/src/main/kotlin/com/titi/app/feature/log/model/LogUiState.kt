package com.titi.app.feature.log.model

import com.airbnb.mvrx.MavericksState
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.extension.getWeekInformation
import com.titi.app.core.designsystem.extension.makeDefaultWeekLineChardData
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.domain.color.model.GraphColor
import java.time.LocalDate

data class LogUiState(
    val tabSelectedIndex: Int = 0,
    val graphColorUiState: GraphColorUiState = GraphColorUiState(),
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
    val graphGoalTime: GraphGoalTime = GraphGoalTime(),
    val totalData: TotalData = TotalData(),
    val homeGraphData: HomeGraphData = HomeGraphData(),
) {
    data class GraphGoalTime(
        val monthGoalTime: Int = 100,
        val weekGoalTime: Int = 30,
    )

    data class TotalData(
        val totalTimeSeconds: Long = 0L,
        val topTotalTdsTaskData: List<TdsTaskData> = emptyList(),
    )

    data class HomeGraphData(
        val homeMonthGraphData: HomeMonthGraphData = HomeMonthGraphData(),
        val homeWeekGraphData: HomeWeekGraphData = HomeWeekGraphData(),
        val homeDailyGraphData: HomeDailyGraphData = HomeDailyGraphData(),
    )

    data class HomeMonthGraphData(
        val totalTimeSeconds: Long = 0,
        val taskData: List<TdsTaskData> = emptyList(),
    )

    data class HomeWeekGraphData(
        val weekInformation: Triple<String, String, String> = LocalDate.now().getWeekInformation(),
        val totalTimeSeconds: Long = 0L,
        val totalWeekTime: String = 0L.getTimeString(),
        val averageWeekTime: String = 0L.getTimeString(),
        val weekLineChartData: List<TdsWeekLineChartData> =
            LocalDate.now().makeDefaultWeekLineChardData(),
    )

    data class HomeDailyGraphData(
        val currentDate: LocalDate = LocalDate.now(),
        val timeLines: List<Long> = LongArray(24) { 0L }.toList(),
    ) {
        val todayDate = currentDate.toString().replace('-', '.')
        val todayDayOfTheWeek = currentDate.dayOfWeek.value - 1
    }
}

data class DailyUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val isCreate: Boolean = true,
    val hasDailies: List<LocalDate> = emptyList(),
    val dailyGraphData: DailyGraphData = DailyGraphData(),
    val checkedButtonStates: List<Boolean> = List(4) { false },
)

data class DailyGraphData(
    val totalTime: String = 0L.getTimeString(),
    val maxTime: String = 0L.getTimeString(),
    val timeLine: List<Long> = LongArray(24) { 0L }.toList(),
    val taskData: List<TdsTaskData> = emptyList(),
    val tdsTimeTableData: List<TdsTimeTableData> = emptyList(),
)

data class WeekUiState(
    val currentDate: LocalDate = LocalDate.now(),
    val isCreate: Boolean = true,
    val hasDailies: List<LocalDate> = emptyList(),
    val weekGraphData: WeekGraphData = WeekGraphData(),
)

data class WeekGraphData(
    val weekInformation: Triple<String, String, String> = LocalDate.now().getWeekInformation(),
    val totalWeekTime: String = 0L.getTimeString(),
    val averageWeekTime: String = 0L.getTimeString(),
    val weekLineChartData: List<TdsWeekLineChartData> =
        LocalDate.now().makeDefaultWeekLineChardData(),
    val topLevelTaskTotal: String = 0L.getTimeString(),
    val topLevelTdsTaskData: List<TdsTaskData> = emptyList(),
)
