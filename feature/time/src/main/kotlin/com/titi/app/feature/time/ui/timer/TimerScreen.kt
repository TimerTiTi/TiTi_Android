package com.titi.app.feature.time.ui.timer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsTimer
import com.titi.app.core.designsystem.extension.getTdsTime
import com.titi.app.core.designsystem.navigation.TdsBottomNavigationBar
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.feature.time.component.TimeAddEditDailyDialog
import com.titi.app.feature.time.component.TimeButtonComponent
import com.titi.app.feature.time.component.TimeCheckDailyDialog
import com.titi.app.feature.time.component.TimeColorDialog
import com.titi.app.feature.time.component.TimeHeaderComponent
import com.titi.app.feature.time.component.TimeTaskComponent
import com.titi.app.feature.time.component.TimeTimerDialog
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.model.TimerUiState
import com.titi.app.feature.time.ui.task.TaskBottomSheet

@Composable
fun TimerScreen(
    splashResultState: SplashResultState,
    isFinish: Boolean,
    onChangeFinishStateFalse: () -> Unit,
    onNavigateToColor: () -> Unit,
    onNavigateToMeasure: (String) -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val viewModel: TimerViewModel = mavericksViewModel(
        argsFactory = {
            splashResultState.asMavericksArgs()
        },
    )

    LaunchedEffect(Unit) {
        viewModel.updateRecordingMode()
    }

    val uiState by viewModel.collectAsState()

    var showTaskBottomSheet by remember { mutableStateOf(false) }
    var showSelectColorDialog by remember { mutableStateOf(false) }
    var showAddEditDailyDialog by remember { mutableStateOf(false) }
    var showCheckTaskDailyDialog by remember { mutableStateOf(false) }
    var showUpdateTimerDialog by remember { mutableStateOf(false) }

    if (showTaskBottomSheet) {
        TaskBottomSheet(
            themeColor = Color(uiState.timerColor.backgroundColor),
            onCloseBottomSheet = { showTaskBottomSheet = false },
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
            onNegative = {
                viewModel.rollBackTimerColor()
            },
            onShowDialog = {
                showSelectColorDialog = it
            },
            onClickBackgroundColor = {
                onNavigateToColor()
            },
            onClickTextColor = {
                viewModel.updateColor(it)
            },
        )
    }

    if (showAddEditDailyDialog) {
        TimeAddEditDailyDialog(
            isFirstDaily = uiState.isFirstDaily,
            todayDate = uiState.todayDate,
            currentTime = uiState.recordTimes.setGoalTime.getTdsTime(),
            onPositive = { goalTime ->
                if (goalTime > 0) {
                    if (uiState.isFirstDaily) {
                        viewModel.addDaily()
                    } else {
                        viewModel.updateSetGoalTime(
                            uiState.recordTimes,
                            goalTime,
                        )

                        onChangeFinishStateFalse()
                    }
                }
            },
            onShowDialog = {
                showAddEditDailyDialog = it
            },
        )
    }

    if (showCheckTaskDailyDialog) {
        TimeCheckDailyDialog(
            title = if (!uiState.isSetTask && uiState.isFirstDaily) {
                stringResource(id = R.string.daily_task_check_title)
            } else if (!uiState.isSetTask) {
                stringResource(id = R.string.task_check_title)
            } else {
                stringResource(id = R.string.daily_check_title)
            },
            onShowDialog = {
                showCheckTaskDailyDialog = it
            },
        )
    }

    if (showUpdateTimerDialog) {
        TimeTimerDialog(
            onPositive = {
                if (it > 0) {
                    viewModel.updateSetTimerTime(
                        uiState.recordTimes,
                        it,
                    )
                    onChangeFinishStateFalse()
                }
            },
            onShowDialog = {
                showUpdateTimerDialog = it
            },
        )
    }

    TimerScreen(
        uiState = uiState,
        isFinish = isFinish,
        textColor = if (uiState.timerColor.isTextColorBlack) {
            Color(0x8C000000)
        } else {
            Color.White
        },
        onClickColor = {
            viewModel.savePrevTimerColor(uiState.timerColor)
            showSelectColorDialog = true
        },
        onClickTask = {
            showTaskBottomSheet = true
        },
        onClickAddEditDaily = {
            showAddEditDailyDialog = true
        },
        onClickStartRecord = {
            if (uiState.isSetTask && !uiState.isFirstDaily) {
                val splashResultStateString = viewModel.startRecording(
                    recordTimes = uiState.recordTimes,
                    daily = uiState.daily,
                    timeColor = uiState.timeColor,
                )
                onNavigateToMeasure(splashResultStateString)
            } else {
                showCheckTaskDailyDialog = true
            }
        },
        onClickSettingTimer = {
            showUpdateTimerDialog = true
        },
        onNavigateToDestination = onNavigateToDestination,
    )
}

@Composable
private fun TimerScreen(
    uiState: TimerUiState,
    isFinish: Boolean,
    textColor: Color,
    onClickColor: () -> Unit,
    onClickTask: () -> Unit,
    onClickAddEditDaily: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickSettingTimer: () -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        containerColor = Color(uiState.timeColor.timerBackgroundColor),
        bottomBar = {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                TdsBottomNavigationBar(
                    currentTopLevelDestination = TopLevelDestination.TIMER,
                    bottomNavigationColor = uiState.timeColor.timerBackgroundColor,
                    onNavigateToDestination = onNavigateToDestination,
                )
            }
        },
    ) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    TimeHeaderComponent(
                        todayDate = uiState.todayDate,
                        textColor = textColor,
                        onClickColor = onClickColor,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TimeTaskComponent(
                        isSetTask = uiState.isSetTask,
                        textColor = textColor,
                        taskName = uiState.taskName,
                        onClickTask = onClickTask,
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    with(uiState.timerRecordTimes) {
                        TdsTimer(
                            isFinish = isFinish,
                            outCircularLineColor = textColor,
                            outCircularProgress = outCircularProgress,
                            inCircularLineTrackColor = if (uiState.timerColor.isTextColorBlack) {
                                Color.White
                            } else {
                                Color(0x8C000000)
                            },
                            inCircularProgress = inCircularProgress,
                            fontColor = textColor,
                            recordingMode = 1,
                            savedSumTime = savedSumTime,
                            savedTime = savedTime,
                            savedGoalTime = savedGoalTime,
                            finishGoalTime = finishGoalTime,
                            isTaskTargetTimeOn = isTaskTargetTimeOn,
                            onClickStopStart = {
                                onClickStartRecord()
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    TimeButtonComponent(
                        recordingMode = 1,
                        tintColor = textColor,
                        isFirstDaily = uiState.isFirstDaily,
                        onClickAddEditDaily = onClickAddEditDaily,
                        onClickStartRecord = onClickStartRecord,
                        onClickSettingTimer = onClickSettingTimer,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            else -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(it),
                    contentAlignment = Alignment.Center,
                ) {
                    with(uiState.timerRecordTimes) {
                        TdsTimer(
                            isFinish = isFinish,
                            outCircularLineColor = textColor,
                            outCircularProgress = outCircularProgress,
                            inCircularLineTrackColor = if (uiState.timerColor.isTextColorBlack) {
                                Color.White
                            } else {
                                Color(0x8C000000)
                            },
                            inCircularProgress = inCircularProgress,
                            fontColor = textColor,
                            recordingMode = 1,
                            savedSumTime = savedSumTime,
                            savedTime = savedTime,
                            savedGoalTime = savedGoalTime,
                            finishGoalTime = finishGoalTime,
                            isTaskTargetTimeOn = isTaskTargetTimeOn,
                            onClickStopStart = {
                                onClickStartRecord()
                            },
                        )
                    }
                }
            }
        }
    }
}
