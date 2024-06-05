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
    checkedButtonStates: List<Boolean>,
    pictureList: List<Picture>,
    onCheckedChange: (page: Int, checked: Boolean) -> Unit,
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
            beyondBoundsPageCount = 2,
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
                    picture = pictureList[0],
                    checked = checkedButtonStates[0],
                    onCheckedChange = {
                        onCheckedChange(0, it)
                    },
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
                    picture = pictureList[1],
                    checked = checkedButtonStates[1],
                    onCheckedChange = {
                        onCheckedChange(1, it)
                    },
                )

                2 -> TdsTimeLineDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList[2],
                    checked = checkedButtonStates[2],
                    onCheckedChange = {
                        onCheckedChange(2, it)
                    },
                )

                3 -> TdsTaskProgressDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    taskData = taskData,
                    tdsColors = tdsColors,
                    picture = pictureList[3],
                    checked = checkedButtonStates[3],
                    onCheckedChange = {
                        onCheckedChange(3, it)
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
