package com.titi.feature.time.ui.time

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.core.designsystem.component.TdsDialog
import com.titi.core.designsystem.component.TdsIconButton
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.component.TdsTimer
import com.titi.core.designsystem.model.TdsDialogInfo
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.core.util.addTimeToNow
import com.titi.designsystem.R
import com.titi.feature.time.content.ColorSelectContent
import com.titi.feature.time.ui.color.ColorActivity
import com.titi.feature.time.ui.color.ColorActivity.Companion.RECORDING_MODE_KEY
import com.titi.feature.time.ui.color.ColorActivity.Companion.TIME_COLOR_KEY
import com.titi.feature.time.ui.task.TaskBottomSheet

@Composable
fun TimeScreen(
    viewModel: TimeViewModel = mavericksViewModel(),
    recordingMode: Int,
    widthDp: Dp,
    heightDp: Dp
) {
    LaunchedEffect(Unit) {
        viewModel.updateRecordingMode(recordingMode)
    }

    val context = LocalContext.current

    val uiState by viewModel.collectAsState()

    var showTaskBottomSheet by remember { mutableStateOf(false) }
    var showSelectColorPopUp by remember { mutableStateOf(false) }

    if (showTaskBottomSheet) {
        TaskBottomSheet(
            modifier = Modifier.height(heightDp - 150.dp),
            themeColor = if (recordingMode == 1) {
                Color(uiState.timeColor.timerBackgroundColor)
            } else {
                Color(uiState.timeColor.stopwatchBackgroundColor)
            },
            onCloseBottomSheet = { showTaskBottomSheet = false }
        )
    }

    if (showSelectColorPopUp) {
        TdsDialog(
            modifier = Modifier.background(color = Color(0xCCFFFFFF)),
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(id = R.string.custom_color),
                positiveText = stringResource(id = R.string.Ok),
                negativeText = stringResource(id = R.string.Cancel),
                onPositive = {},
                onNegative = {
                    viewModel.rollBackTimeColor()
                },
            ),
            onShowDialog = {
                showSelectColorPopUp = it
            }
        ) {
            ColorSelectContent(
                backgroundColor = if (recordingMode == 1) {
                    Color(uiState.timeColor.timerBackgroundColor)
                } else {
                    Color(uiState.timeColor.stopwatchBackgroundColor)
                },
                textColor = if (recordingMode == 1) {
                    if (uiState.timeColor.isTimerBlackTextColor) {
                        Color.Black
                    } else {
                        Color.White
                    }
                } else {
                    if (uiState.timeColor.isStopwatchBlackTextColor) {
                        Color.Black
                    } else {
                        Color.White
                    }
                },
                onClickBackgroundColor = {
                    context.startActivity(
                        Intent(
                            context,
                            ColorActivity::class.java
                        ).apply {
                            putExtra(RECORDING_MODE_KEY, recordingMode)
                            putExtra(TIME_COLOR_KEY, uiState.timeColor)
                        }
                    )
                },
                onClickTextColor = {
                    val updateColor = if (recordingMode == 1) {
                        uiState.timeColor.copy(isTimerBlackTextColor = it)
                    } else {
                        uiState.timeColor.copy(isStopwatchBlackTextColor = it)
                    }
                    viewModel.updateColor(updateColor)
                }
            )
        }
    }

    TimeScreen(
        recordingMode = recordingMode,
        backgroundColor = if (recordingMode == 1) {
            Color(uiState.timeColor.timerBackgroundColor)
        } else {
            Color(uiState.timeColor.stopwatchBackgroundColor)
        },
        textColor = if (recordingMode == 1) {
            if (uiState.timeColor.isTimerBlackTextColor) {
                TdsColor.blackColor
            } else {
                TdsColor.whiteColor
            }
        } else {
            if (uiState.timeColor.isStopwatchBlackTextColor) {
                TdsColor.blackColor
            } else {
                TdsColor.whiteColor
            }
        },
        uiState = uiState,
        onClickColor = {
            viewModel.savePrevTimeColor(uiState.timeColor)
            showSelectColorPopUp = true
        },
        onClickTask = {
            showTaskBottomSheet = true
        },
        onClickAddRecord = {},
        onClickStartRecord = {},
        onClickSettingTime = {},
    )
}

@Composable
private fun TimeScreen(
    recordingMode: Int,
    backgroundColor: Color,
    textColor: TdsColor,
    uiState: TimeUiState,
    onClickColor: () -> Unit,
    onClickTask: () -> Unit,
    onClickAddRecord: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickSettingTime: () -> Unit,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            //TODO 상태에 따른 컬러값
            TdsText(
                modifier = Modifier.align(Alignment.Center),
                text = uiState.todayDate,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 16.sp,
                color = textColor
            )

            TdsIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onClickColor,
                size = 32.dp
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.color_selector_icon),
                    contentDescription = "setColorIcon",
                    tint = Color.Unspecified
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.recordTimes.recordTask == null) {
            OutlinedButton(
                onClick = onClickTask,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, TdsColor.redColor.getColor()),
                contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp)
            ) {
                TdsText(
                    text = stringResource(R.string.create_task_text),
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 18.sp,
                    color = TdsColor.redColor,
                )
            }
        } else {
            OutlinedButton(
                onClick = onClickTask,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, textColor.getColor()),
                contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp)
            ) {
                TdsText(
                    text = uiState.recordTimes.recordTask,
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 18.sp,
                    color = textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        with(uiState.recordTimes) {
            TdsTimer(
                outCircularLineColor = textColor,
                outCircularProgress = if (recordingMode == 1) {
                    ((setTimerTime - savedTimerTime) / setTimerTime).toFloat()
                } else {
                    (savedStopWatchTime / 3600).toFloat()
                },
                inCircularLineTrackColor = if (textColor == TdsColor.whiteColor) {
                    TdsColor.blackColor
                } else {
                    TdsColor.whiteColor
                },
                inCircularProgress = (savedSumTime / setGoalTime).toFloat(),
                fontColor = textColor,
                recordingMode = recordingMode,
                savedSumTime = savedSumTime,
                savedTime = if (recordingMode == 1) savedTimerTime else savedStopWatchTime,
                savedGoalTime = savedGoalTime,
                finishGoalTime = addTimeToNow(savedGoalTime),
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TdsIconButton(onClick = onClickAddRecord, size = 50.dp) {
                Icon(
                    painter = painterResource(id = R.drawable.add_record_icon),
                    contentDescription = "addRecord",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(25.dp))

            TdsIconButton(onClick = onClickStartRecord, size = 70.dp) {
                Icon(
                    painter = painterResource(id = R.drawable.start_record_icon),
                    contentDescription = "startRecord",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(25.dp))

            TdsIconButton(onClick = onClickSettingTime, size = 50.dp) {
                Icon(
                    painter = painterResource(
                        id = if (recordingMode == 1) {
                            R.drawable.setting_timer_time_icon
                        } else {
                            R.drawable.setting_stopwatch_time_icon
                        }
                    ),
                    contentDescription = "addRecord",
                    tint = Color.Unspecified
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun TimeScreenPreview() {
    TiTiTheme {
        TimeScreen(
            recordingMode = 1,
            backgroundColor = Color.Blue,
            textColor = TdsColor.blackColor,
            uiState = TimeUiState(),
            onClickColor = {},
            onClickTask = {},
            onClickAddRecord = {},
            onClickStartRecord = {},
            onClickSettingTime = {},
        )
    }
}