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
import androidx.compose.foundation.layout.padding
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
import com.titi.app.core.designsystem.component.TdsStandardWeekGraph
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.ui.component.ButtonRow
import com.titi.app.feature.log.ui.component.CalendarContent
import com.titi.app.feature.log.util.saveBitmapFromComposableWithPermission
import com.titi.app.feature.log.util.saveDailyGraph
import com.titi.app.feature.log.util.shareDailyGraph
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun WeekScreen(
    totalTime: String,
    averageTime: String,
    hasDailies: List<LocalDate>,
    weekLineChardData: List<TdsWeekLineChartData>,
    weekInformation: Triple<String, String, String>,
    tdsColors: List<TdsColor>,
    topLevelTaskData: List<TdsTaskData>,
    topLevelTaskTotal: String,
    currentDate: LocalDate,
    onClickDate: (LocalDate) -> Unit,
    onClickGraphColor: (Int) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val pictureList = remember {
        List(1) { Picture() }
    }
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }

    val requestWritePermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                coroutineScope.launch {
                    val message = saveDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = listOf(true),
                    )
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
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

        ButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            onSaveClick = {
                saveBitmapFromComposableWithPermission(
                    coroutineScope = coroutineScope,
                    context = context,
                    pictureList = pictureList,
                    checkedButtonStates = listOf(true),
                    permissionLauncher = requestWritePermissionLauncher,
                )
            },
            onShareClick = {
                coroutineScope.launch {
                    shareDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = listOf(true),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsColorRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp),
            onClick = onClickGraphColor,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsStandardWeekGraph(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            totalTime = totalTime,
            averageTime = averageTime,
            weekInformation = weekInformation,
            weekLineChardData = weekLineChardData,
            tdsColors = tdsColors,
            topLevelTaskData = topLevelTaskData,
            topLevelTaskTotal = topLevelTaskTotal,
            picture = pictureList[0],
        )
    }
}

@Preview
@Composable
private fun WeekScreenPreview() {
    val weekLineChardData = listOf(
        TdsWeekLineChartData(
            time = 6200,
            date = "1/12",
        ),
        TdsWeekLineChartData(
            time = 3700,
            date = "1/13",
        ),
        TdsWeekLineChartData(
            time = 5200,
            date = "1/14",
        ),
        TdsWeekLineChartData(
            time = 1042,
            date = "1/15",
        ),
        TdsWeekLineChartData(
            time = 4536,
            date = "1/16",
        ),
        TdsWeekLineChartData(
            time = 3700,
            date = "1/17",
        ),
        TdsWeekLineChartData(
            time = 2455,
            date = "1/18",
        ),
    )

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

    TiTiTheme {
        WeekScreen(
            weekInformation = Triple("2024.02", "Week 2", "02.12~02.19"),
            totalTime = "08:00:00",
            averageTime = "03:00:00",
            weekLineChardData = weekLineChardData,
            tdsColors = tdsColors,
            hasDailies = emptyList(),
            topLevelTaskData = taskData,
            topLevelTaskTotal = "08:00:00",
            currentDate = LocalDate.now(),
            onClickDate = {},
            onClickGraphColor = {},
            onCalendarLocalDateChanged = {},
        )
    }
}
