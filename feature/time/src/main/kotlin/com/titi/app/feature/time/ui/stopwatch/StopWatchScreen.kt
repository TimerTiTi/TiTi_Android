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
import com.titi.app.core.util.toJson
import com.titi.app.feature.time.content.TimeButtonContent
import com.titi.app.feature.time.content.TimeCheckDailyDialog
import com.titi.app.feature.time.content.TimeColorDialog
import com.titi.app.feature.time.content.TimeDailyDialog
import com.titi.app.feature.time.content.TimeHeaderContent
import com.titi.app.feature.time.content.TimeTaskContent
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.model.StopWatchUiState
import com.titi.app.feature.time.ui.task.TaskBottomSheet
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

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
    var showAddDailyDialog by remember { mutableStateOf(false) }
    var showCheckTaskDailyDialog by remember { mutableStateOf(false) }

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

    if (showAddDailyDialog) {
        TimeDailyDialog(
            todayDate = uiState.todayDate,
            currentTime = uiState.recordTimes.setGoalTime.getTdsTime(),
            onPositive = {
                if (it > 0) {
                    viewModel.updateSetGoalTime(
                        uiState.recordTimes,
                        it,
                    )
                    viewModel.addDaily()
                }
            },
            onShowDialog = {
                showAddDailyDialog = it
            },
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
        onClickAddDaily = {
            showAddDailyDialog = true
        },
        onClickStartRecord = {
            if (uiState.isEnableStartRecording) {
                val updateRecordTimes =
                    uiState.recordTimes.copy(
                        recording = true,
                        recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                    )

                viewModel.updateMeasuringState(updateRecordTimes)

                val splashResultStateString =
                    SplashResultState(
                        recordTimes = updateRecordTimes,
                        timeColor = uiState.timeColor,
                        daily = uiState.daily,
                    ).toJson()

                onNavigateToMeasure(splashResultStateString)
            } else {
                showCheckTaskDailyDialog = true
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
    onClickAddDaily: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickResetStopWatch: () -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Scaffold(
                containerColor = Color(uiState.timeColor.stopwatchBackgroundColor),
                bottomBar = {
                    TdsBottomNavigationBar(
                        currentTopLevelDestination = TopLevelDestination.STOPWATCH,
                        bottomNavigationColor = uiState.timeColor.stopwatchBackgroundColor,
                        onNavigateToDestination = onNavigateToDestination,
                    )
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
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

                    TimeButtonContent(
                        recordingMode = 2,
                        isDailyAfter6AM = uiState.isDailyAfter6AM,
                        tintColor = textColor,
                        onClickAddDaily = onClickAddDaily,
                        onClickStartRecord = onClickStartRecord,
                        onClickResetStopwatch = onClickResetStopWatch,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        else -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentAlignment = Alignment.Center,
            ) {
                with(uiState.stopWatchRecordTimes) {
                    TdsTimer(
                        outCircularLineColor = textColor,
                        outCircularProgress = outCircularProgress,
                        inCircularLineTrackColor = if (uiState.stopWatchColor.isTextColorBlack) {
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
