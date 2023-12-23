package com.titi.feature.time.ui.timer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.core.designsystem.component.TdsTimer
import com.titi.core.designsystem.theme.TdsColor
import com.titi.designsystem.R
import com.titi.feature.time.SplashResultState
import com.titi.feature.time.content.TimeButtonContent
import com.titi.feature.time.content.TimeCheckDailyDialog
import com.titi.feature.time.content.TimeColorDialog
import com.titi.feature.time.content.TimeDailyDialog
import com.titi.feature.time.content.TimeHeaderContent
import com.titi.feature.time.content.TimeTaskContent
import com.titi.feature.time.content.TimeTimerDialog
import com.titi.feature.time.ui.measure.MeasuringActivity
import com.titi.feature.time.ui.task.TaskBottomSheet
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

@Composable
fun TimerScreen(
    splashResultState: SplashResultState,
    widthDp: Dp,
    heightDp: Dp
) {
    val context = LocalContext.current
    val viewModel: TimerViewModel = mavericksViewModel(
        argsFactory = {
            splashResultState.asMavericksArgs()
        }
    )

    LaunchedEffect(Unit) {
        viewModel.updateRecordingMode()
    }

    val uiState by viewModel.collectAsState()

    var showTaskBottomSheet by remember { mutableStateOf(false) }
    var showSelectColorDialog by remember { mutableStateOf(false) }
    var showAddDailyDialog by remember { mutableStateOf(false) }
    var showCheckTaskDailyDialog by remember { mutableStateOf(false) }
    var showUpdateTimerDialog by remember { mutableStateOf(false) }

    if (showTaskBottomSheet) {
        TaskBottomSheet(
            modifier = Modifier.height(heightDp - 150.dp),
            themeColor = Color(uiState.timerColor.backgroundColor),
            onCloseBottomSheet = { showTaskBottomSheet = false }
        )
    }

    if (showSelectColorDialog) {
        TimeColorDialog(
            backgroundColor = Color(uiState.timerColor.backgroundColor),
            textColor = if (uiState.timerColor.isTextColorBlack) {
                Color.Black
            } else {
                Color.White
            },
            recordingMode = 1,
            onNegative = {
                viewModel.rollBackTimerColor()
            },
            onShowDialog = {
                showSelectColorDialog = it
            },
            onClickTextColor = {
                viewModel.updateColor(it)
            }
        )
    }

    if (showAddDailyDialog) {
        TimeDailyDialog(
            todayDate = uiState.todayDate,
            onPositive = {
                if (it > 0) {
                    viewModel.updateSetGoalTime(
                        uiState.recordTimes,
                        it
                    )
                    viewModel.addDaily()
                }
            },
            onShowDialog = {
                showAddDailyDialog = it
            }
        )
    }

    if (showCheckTaskDailyDialog) {
        TimeCheckDailyDialog(
            title = if (!uiState.isSetTask && !uiState.isDailyAfter6AM) {
                stringResource(id = R.string.daily_task_check_title)
            } else if (!uiState.isSetTask) {
                stringResource(id = R.string.task_check_title)
            } else {
                stringResource(id = R.string.daily_check_title)
            },
            onShowDialog = {
                showCheckTaskDailyDialog = it
            }
        )
    }

    if (showUpdateTimerDialog) {
        TimeTimerDialog(
            onPositive = {
                if (it > 0) {
                    viewModel.updateSetTimerTime(
                        uiState.recordTimes,
                        it
                    )
                }
            },
            onShowDialog = {
                showUpdateTimerDialog = it
            }
        )
    }

    TimerScreen(
        uiState = uiState,
        backgroundColor = Color(uiState.timerColor.backgroundColor),
        textColor = if (uiState.timerColor.isTextColorBlack) {
            TdsColor.blackColor
        } else {
            TdsColor.whiteColor
        },
        onClickColor = {
            viewModel.savePrevTimerColor(uiState.timerColor)
            showSelectColorDialog = true
        },
        onClickTask = {
            showTaskBottomSheet = true
        },
        onClickAddDaily = {
            showAddDailyDialog = true
        },
        onClickStartRecord = {
            if (uiState.isEnableStartRecording) {
                val updateRecordTimes = uiState.recordTimes.copy(
                    recording = true,
                    recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString()
                )

                viewModel.updateMeasuringState(updateRecordTimes)

                context.startActivity(
                    Intent(
                        context,
                        MeasuringActivity::class.java
                    ).apply {
                        putExtra(
                            MeasuringActivity.RECORD_TIMES_KEY,
                            updateRecordTimes
                        )
                        putExtra(
                            MeasuringActivity.BACKGROUND_COLOR_KEY,
                            uiState.timeColor
                        )
                    }
                )
            } else {
                showCheckTaskDailyDialog = true
            }
        },
        onClickSettingTimer = {
            showUpdateTimerDialog = true
        }
    )
}

@Composable
private fun TimerScreen(
    uiState: TimerUiState,
    backgroundColor: Color,
    textColor: TdsColor,
    onClickColor: () -> Unit,
    onClickTask: () -> Unit,
    onClickAddDaily: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickSettingTimer: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(top = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeHeaderContent(
            todayDate = uiState.todayDate,
            isDailyAfter6AM = uiState.isDailyAfter6AM,
            textColor = textColor,
            onClickColor = onClickColor,
        )

        Spacer(modifier = Modifier.weight(1f))

        TimeTaskContent(
            isSetTask = uiState.isSetTask,
            textColor = textColor,
            taskName = uiState.taskName,
            onClickTask = onClickTask
        )

        Spacer(modifier = Modifier.height(50.dp))

        with(uiState.timerRecordTimes) {
            TdsTimer(
                outCircularLineColor = textColor.getColor(),
                outCircularProgress = outCircularProgress,
                inCircularLineTrackColor = if (textColor == TdsColor.whiteColor) {
                    TdsColor.blackColor
                } else {
                    TdsColor.whiteColor
                },
                inCircularProgress = inCircularProgress,
                fontColor = textColor,
                recordingMode = 1,
                savedSumTime = savedSumTime,
                savedTime = savedTime,
                savedGoalTime = savedGoalTime,
                finishGoalTime = finishGoalTime,
                isTaskTargetTimeOn = isTaskTargetTimeOn
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        TimeButtonContent(
            recordingMode = 1,
            isDailyAfter6AM = uiState.isDailyAfter6AM,
            onClickAddDaily = onClickAddDaily,
            onClickStartRecord = onClickStartRecord,
            onClickSettingTimer = onClickSettingTimer
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}