package com.titi.app.feature.log.ui

import android.Manifest
import android.graphics.Picture
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsPagerIndicator
import com.titi.app.core.designsystem.component.TdsStandardDailyGraph
import com.titi.app.core.designsystem.component.TdsTaskProgressDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeLineDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeTableDailyGraph
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.ui.component.ButtonRow
import com.titi.app.feature.log.ui.component.CalendarContent
import com.titi.app.feature.log.util.saveDailyGraph
import com.titi.app.feature.log.util.saveDailyGraphWithPermission
import com.titi.app.feature.log.util.shareDailyGraph
import java.time.LocalDate

@Composable
fun DailyScreen(
    currentDate: LocalDate,
    totalTime: String,
    maxTime: String,
    hasDailies: List<LocalDate>,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    timeTableData: List<TdsTimeTableData>,
    checkedButtonStates: List<Boolean>,
    onClickDate: (LocalDate) -> Unit,
    onClickGraphColor: (Int) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
    onCheckedChange: (page: Int, checked: Boolean) -> Unit,
    onNavigateToEdit: () -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val pictureList = remember {
        List(4) { Picture() }
    }
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }

    val requestWritePermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                val message = saveDailyGraph(
                    coroutineScope = coroutineScope,
                    context = context,
                    pictureList = pictureList,
                    checkedButtonStates = checkedButtonStates,
                )
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            } else {
                showPermissionDialog = true
            }
        }

    if (showPermissionDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = "사진을 저장하기 위해서 권한이 필요로 합니다.",
                message = "허용하시겠습니까?",
                positiveText = stringResource(id = R.string.Ok),
                negativeText = stringResource(id = R.string.Cancel),
                onPositive = {
                    requestWritePermissionLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                },
            ),
            onShowDialog = { showPermissionDialog = it },
        ) {
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        CalendarContent(
            modifier = Modifier.fillMaxWidth(),
            themeColor = tdsColors.first(),
            currentDate = currentDate,
            hasDailies = hasDailies,
            onClickDate = onClickDate,
            onCalendarLocalDateChanged = onCalendarLocalDateChanged,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsColorRow(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickGraphColor,
        )

        Spacer(modifier = Modifier.height(15.dp))

        ButtonRow(
            modifier = Modifier.fillMaxWidth(),
            onSaveClick = {
                if (checkedButtonStates.any { it }) {
                    saveDailyGraphWithPermission(
                        coroutineScope = coroutineScope,
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = checkedButtonStates,
                        permissionLauncher = requestWritePermissionLauncher,
                    )
                } else {
                    Toast.makeText(context, "선택된 그래프가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onShareClick = {
                if (checkedButtonStates.any { it }) {
                    shareDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = checkedButtonStates,
                    )
                } else {
                    Toast.makeText(context, "선택된 그래프가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onCreateClick = onNavigateToEdit,
        )

        Spacer(modifier = Modifier.height(15.dp))

        GraphContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = currentDate.toString().replace('-', '.'),
            todayDayOfTheWeek = currentDate.dayOfWeek.value - 1,
            totalTime = totalTime,
            maxTime = maxTime,
            taskData = taskData,
            tdsColors = tdsColors,
            timeLines = timeLines,
            timeTableData = timeTableData,
            checkedButtonStates = checkedButtonStates,
            pictureList = pictureList,
            onCheckedChange = onCheckedChange,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GraphContent(
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

@Composable
@Preview
private fun DailyScreenPreview() {
    val taskData = listOf(
        TdsTaskData(
            key = "수업",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "인공지능",
            value = "03:00:00",
            progress = 0.3f,
        ),
        TdsTaskData(
            key = "알고리즘",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "개발",
            value = "03:00:00",
            progress = 0.3f,
        ),
    )

    val tdsColors = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D11,
        TdsColor.D12,
    )

    val timeLines = listOf(
        3600L,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
    )

    val timeTableData = listOf(
        TdsTimeTableData(
            hour = 3,
            start = 1800,
            end = 2400,
        ),
        TdsTimeTableData(
            hour = 5,
            start = 1234,
            end = 2555,
        ),
        TdsTimeTableData(
            hour = 12,
            start = 600,
            end = 3444,
        ),
        TdsTimeTableData(
            hour = 23,
            start = 2121,
            end = 3333,
        ),
    )

    TiTiTheme {
        DailyScreen(
            taskData = taskData,
            tdsColors = tdsColors,
            totalTime = "08:00:00",
            maxTime = "03:00:00",
            hasDailies = emptyList(),
            timeLines = timeLines,
            timeTableData = timeTableData,
            currentDate = LocalDate.now(),
            checkedButtonStates = List(4) { false },
            onClickDate = {},
            onClickGraphColor = {},
            onCalendarLocalDateChanged = {},
            onCheckedChange = { _, _ -> },
            onNavigateToEdit = {},
        )
    }
}
