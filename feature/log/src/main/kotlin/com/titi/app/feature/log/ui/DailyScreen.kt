package com.titi.app.feature.log.ui

import android.Manifest
import android.graphics.Picture
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsGraphContent
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.model.DailyGraphData
import com.titi.app.feature.log.model.DailyUiState
import com.titi.app.feature.log.ui.component.ButtonRow
import com.titi.app.feature.log.ui.component.CalendarContent
import com.titi.app.feature.log.util.saveDailyGraph
import com.titi.app.feature.log.util.saveDailyGraphWithPermission
import com.titi.app.feature.log.util.shareDailyGraph
import java.time.LocalDate

@Composable
fun DailyScreen(
    dailyUiState: DailyUiState,
    tdsColors: List<TdsColor>,
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
                    checkedButtonStates = dailyUiState.checkedButtonStates,
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
            currentDate = dailyUiState.currentDate,
            hasDailies = dailyUiState.hasDailies,
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
            isCreate = dailyUiState.isCreate,
            onSaveClick = {
                if (dailyUiState.checkedButtonStates.any { it }) {
                    saveDailyGraphWithPermission(
                        coroutineScope = coroutineScope,
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = dailyUiState.checkedButtonStates,
                        permissionLauncher = requestWritePermissionLauncher,
                    )
                } else {
                    Toast.makeText(context, "선택된 그래프가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onShareClick = {
                if (dailyUiState.checkedButtonStates.any { it }) {
                    shareDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = dailyUiState.checkedButtonStates,
                    )
                } else {
                    Toast.makeText(context, "선택된 그래프가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onCreateEditClick = onNavigateToEdit,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsGraphContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = dailyUiState.currentDate.toString().replace('-', '.'),
            todayDayOfTheWeek = dailyUiState.currentDate.dayOfWeek.value - 1,
            totalTime = dailyUiState.dailyGraphData.totalTime,
            maxTime = dailyUiState.dailyGraphData.maxTime,
            taskData = dailyUiState.dailyGraphData.taskData,
            tdsColors = tdsColors,
            timeLines = dailyUiState.dailyGraphData.timeLine,
            timeTableData = dailyUiState.dailyGraphData.tdsTimeTableData,
            checkedButtonStates = dailyUiState.checkedButtonStates,
            pictureList = pictureList,
            onCheckedChange = onCheckedChange,
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
            color = TdsColor.D1,
            hour = 3,
            start = 1800,
            end = 2400,
        ),
        TdsTimeTableData(
            color = TdsColor.D1,
            hour = 5,
            start = 1234,
            end = 2555,
        ),
        TdsTimeTableData(
            color = TdsColor.D1,
            hour = 12,
            start = 600,
            end = 3444,
        ),
        TdsTimeTableData(
            color = TdsColor.D1,
            hour = 23,
            start = 2121,
            end = 3333,
        ),
    )

    TiTiTheme {
        DailyScreen(
            dailyUiState = DailyUiState(
                currentDate = LocalDate.now(),
                isCreate = false,
                hasDailies = listOf(),
                dailyGraphData = DailyGraphData(
                    totalTime = "03:00:00",
                    maxTime = "03:00:00",
                    timeLine = timeLines,
                    taskData = taskData,
                    tdsTimeTableData = timeTableData,
                ),
                checkedButtonStates = listOf(),
            ),
            tdsColors = tdsColors,
            onClickDate = {},
            onClickGraphColor = {},
            onCalendarLocalDateChanged = {},
            onCheckedChange = { _, _ -> },
            onNavigateToEdit = {},
        )
    }
}
