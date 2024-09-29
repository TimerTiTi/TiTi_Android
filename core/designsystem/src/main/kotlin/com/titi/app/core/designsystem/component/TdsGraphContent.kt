package com.titi.app.core.designsystem.component

import android.graphics.Picture
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TdsGraphContent(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    totalTime: String,
    maxTime: String,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    timeTableData: List<TdsTimeTableData>,
    checkedButtonStates: List<Boolean> = emptyList(),
    pictureList: List<Picture> = emptyList(),
    onCheckedChange: ((page: Int, checked: Boolean) -> Unit)? = null,
    selectedTaskIndex: Int? = null,
    onClickTask: ((taskName: String, index: Int) -> Unit)? = null,
    onClickAddTask: (() -> Unit)? = null,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            4
        },
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
    ) {
        HorizontalPager(
            modifier = Modifier.wrapContentSize(),
            userScrollEnabled = true,
            state = pagerState,
            beyondViewportPageCount = 3,
        ) { page ->
            when (page % 4) {
                0 -> TdsStandardDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    taskData = taskData,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList.getOrNull(0),
                    checked = checkedButtonStates.getOrNull(0),
                    onCheckedChange = if (onCheckedChange != null) {
                        { onCheckedChange(0, it) }
                    } else {
                        null
                    },
                    selectedTaskIndex = selectedTaskIndex,
                    onClickTask = onClickTask,
                    onClickAddTask = onClickAddTask,
                )

                1 -> TdsTimeTableDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    taskData = taskData,
                    timeTableData = timeTableData,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList.getOrNull(1),
                    checked = checkedButtonStates.getOrNull(1),
                    onCheckedChange = if (onCheckedChange != null) {
                        { onCheckedChange(1, it) }
                    } else {
                        null
                    },
                    selectedTaskIndex = selectedTaskIndex,
                    onClickTask = onClickTask,
                    onClickAddTask = onClickAddTask,
                )

                2 -> TdsTimeLineDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList.getOrNull(2),
                    checked = checkedButtonStates.getOrNull(2),
                    onCheckedChange = if (onCheckedChange != null) {
                        { onCheckedChange(2, it) }
                    } else {
                        null
                    },
                )

                3 -> TdsTaskProgressDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    taskData = taskData,
                    tdsColors = tdsColors,
                    picture = pictureList.getOrNull(3),
                    checked = checkedButtonStates.getOrNull(3),
                    onCheckedChange = if (onCheckedChange != null) {
                        { onCheckedChange(3, it) }
                    } else {
                        null
                    },
                )
            }
        }

        TdsPagerIndicator(
            modifier = Modifier.padding(bottom = 8.dp),
            pagerState = pagerState,
            indicatorCount = 4,
            indicatorSize = 8.dp,
            activeColor = TdsColor.TEXT.getColor(),
        )
    }
}
