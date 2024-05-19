package com.titi.app.feature.time.ui.stopwatch

import android.content.res.Configuration
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
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.component.TdsTimer
import com.titi.app.core.designsystem.extension.getTdsTime
import com.titi.app.core.designsystem.navigation.TdsBottomNavigationBar
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.feature.time.component.TimeButtonComponent
import com.titi.app.feature.time.component.TimeCheckTaskDialog
import com.titi.app.feature.time.component.TimeColorDialog
import com.titi.app.feature.time.component.TimeGoalTimeEditDialog
import com.titi.app.feature.time.component.TimeHeaderComponent
import com.titi.app.feature.time.component.TimeTaskComponent
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.model.StopWatchUiState
import com.titi.app.feature.time.ui.task.TaskBottomSheet

@Composable
fun StopWatchScreen(
    splashResultState: SplashResultState,
    onNavigateToColor: () -> Unit,
    onNavigateToMeasure: (String) -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val viewModel: StopWatchViewModel = mavericksViewModel(
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
    var showGoalTimeEditDialog by remember { mutableStateOf(false) }
    var showCheckTaskDialog by remember { mutableStateOf(false) }

    if (showTaskBottomSheet) {
        TaskBottomSheet(
            themeColor = Color(uiState.stopWatchColor.backgroundColor),
            onCloseBottomSheet = { showTaskBottomSheet = false },
        )
    }

    if (showSelectColorDialog) {
        TimeColorDialog(
            backgroundColor = Color(uiState.stopWatchColor.backgroundColor),
            textColor = if (uiState.stopWatchColor.isTextColorBlack) {
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
            onClickBackgroundColor = onNavigateToColor,
            onClickTextColor = {
                viewModel.updateColor(it)
            },
        )
    }

    if (showGoalTimeEditDialog) {
        TimeGoalTimeEditDialog(
            todayDate = uiState.todayDate,
            currentTime = uiState.recordTimes.setGoalTime.getTdsTime(),
            onPositive = { goalTime ->
                if (goalTime > 0) {
                    viewModel.updateSetGoalTime(
                        uiState.recordTimes,
                        goalTime,
                    )
                }
            },
            onShowDialog = {
                showGoalTimeEditDialog = it
            },
        )
    }

    if (showCheckTaskDialog) {
        TimeCheckTaskDialog(
            onShowDialog = {
                showCheckTaskDialog = it
            },
        )
    }

    StopWatchScreen(
        uiState = uiState,
        textColor = if (uiState.stopWatchColor.isTextColorBlack) {
            Color(0x8C000000)
        } else {
            Color.White
        },
        onClickColor = {
            viewModel.savePrevTimerColor(uiState.stopWatchColor)
            showSelectColorDialog = true
        },
        onClickTask = {
            showTaskBottomSheet = true
        },
        onClickGoalTimeEdit = {
            showGoalTimeEditDialog = true
        },
        onClickStartRecord = {
            if (uiState.isSetTask) {
                val splashResultStateString = viewModel.startRecording(
                    recordTimes = uiState.recordTimes,
                    daily = uiState.daily,
                    timeColor = uiState.timeColor,
                )
                onNavigateToMeasure(splashResultStateString)
            } else {
                showGoalTimeEditDialog = true
            }
        },
        onClickResetStopWatch = {
            viewModel.updateSavedStopWatchTime(uiState.recordTimes)
        },
        onNavigateToDestination = onNavigateToDestination,
    )
}

@Composable
private fun StopWatchScreen(
    uiState: StopWatchUiState,
    textColor: Color,
    onClickColor: () -> Unit,
    onClickTask: () -> Unit,
    onClickGoalTimeEdit: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickResetStopWatch: () -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        containerColor = Color(uiState.timeColor.stopwatchBackgroundColor),
        bottomBar = {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                TdsBottomNavigationBar(
                    currentTopLevelDestination = TopLevelDestination.STOPWATCH,
                    bottomNavigationColor = uiState.timeColor.stopwatchBackgroundColor,
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

                    with(uiState.stopWatchRecordTimes) {
                        TdsTimer(
                            outCircularLineColor = textColor,
                            outCircularProgress = outCircularProgress,
                            inCircularLineTrackColor =
                            if (uiState.stopWatchColor.isTextColorBlack) {
                                Color.White
                            } else {
                                Color(0x8C000000)
                            },
                            inCircularProgress = inCircularProgress,
                            fontColor = textColor,
                            recordingMode = 2,
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
                        recordingMode = 2,
                        tintColor = textColor,
                        onClickGoalTimeEdit = onClickGoalTimeEdit,
                        onClickStartRecord = onClickStartRecord,
                        onClickResetStopwatch = onClickResetStopWatch,
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
                    with(uiState.stopWatchRecordTimes) {
                        TdsTimer(
                            outCircularLineColor = textColor,
                            outCircularProgress = outCircularProgress,
                            inCircularLineTrackColor =
                            if (uiState.stopWatchColor.isTextColorBlack) {
                                Color.White
                            } else {
                                Color(0x8C000000)
                            },
                            inCircularProgress = inCircularProgress,
                            fontColor = textColor,
                            recordingMode = 2,
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
