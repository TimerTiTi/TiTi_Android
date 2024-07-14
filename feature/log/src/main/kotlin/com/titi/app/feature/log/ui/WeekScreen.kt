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
import com.titi.app.core.designsystem.component.TdsStandardWeekGraph
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsWeekLineChartData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.log.ui.component.ButtonRow
import com.titi.app.feature.log.ui.component.CalendarContent
import com.titi.app.feature.log.util.saveWeekGraph
import com.titi.app.feature.log.util.saveWeekGraphWithPermission
import com.titi.app.feature.log.util.shareWeekGraph
import java.time.LocalDate

@Composable
fun WeekScreen(
    totalTime: String,
    averageTime: String,
    hasDailies: List<LocalDate>,
    weekLineChartData: List<TdsWeekLineChartData>,
    weekInformation: Triple<String, String, String>,
    tdsColors: List<TdsColor>,
    topLevelTaskData: List<TdsTaskData>,
    topLevelTaskTotal: String,
    currentDate: LocalDate,
    onClickDate: (LocalDate) -> Unit,
    onClickGraphColor: (Int) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
    onNavigateToEdit: () -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val picture = remember {
        Picture()
    }
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }

    val requestWritePermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                val message = saveWeekGraph(
                    context = context,
                    coroutineScope = coroutineScope,
                    picture = picture,
                )
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            } else {
                showPermissionDialog = true
            }
        }

    if (showPermissionDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(R.string.daily_popup_savepermissiontitle),
                message = stringResource(R.string.daily_popup_savepermissiondesc),
                positiveText = stringResource(id = R.string.common_text_ok),
                negativeText = stringResource(id = R.string.common_text_cancel),
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
            isCreate = !hasDailies.contains(currentDate),
            onSaveClick = {
                saveWeekGraphWithPermission(
                    coroutineScope = coroutineScope,
                    context = context,
                    picture = picture,
                    permissionLauncher = requestWritePermissionLauncher,
                )
            },
            onShareClick = {
                shareWeekGraph(
                    context = context,
                    picture = picture,
                )
            },
            onCreateEditClick = onNavigateToEdit,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsStandardWeekGraph(
            modifier = Modifier.fillMaxWidth(),
            totalTime = totalTime,
            averageTime = averageTime,
            weekInformation = weekInformation,
            weekLineChartData = weekLineChartData,
            tdsColors = tdsColors,
            topLevelTaskData = topLevelTaskData,
            topLevelTaskTotal = topLevelTaskTotal,
            picture = picture,
        )
    }
}

@Preview
@Composable
private fun WeekScreenPreview() {
    val weekLineChartData = listOf(
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
            weekLineChartData = weekLineChartData,
            tdsColors = tdsColors,
            hasDailies = emptyList(),
            topLevelTaskData = taskData,
            topLevelTaskTotal = "08:00:00",
            currentDate = LocalDate.now(),
            onClickDate = {},
            onClickGraphColor = {},
            onCalendarLocalDateChanged = {},
            onNavigateToEdit = {},
        )
    }
}
