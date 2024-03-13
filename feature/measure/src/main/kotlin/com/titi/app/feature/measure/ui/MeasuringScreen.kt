package com.titi.app.feature.measure.ui

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimer
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.ui.setBrightness
import com.titi.app.core.util.fromJson
import com.titi.app.feature.measure.model.MeasuringUiState
import com.titi.app.feature.measure.model.SplashResultState
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

@Composable
fun MeasuringScreen(splashResultState: String, onFinish: (isFinish: Boolean) -> Unit) {
    val splashResultStateModel =
        splashResultState.fromJson<SplashResultState>() ?: SplashResultState()

    val viewModel: MeasuringViewModel =
        mavericksViewModel(
            argsFactory = {
                splashResultStateModel.asMavericksArgs()
            },
        )

    val uiState by viewModel.collectAsState()
    val context = LocalContext.current
    var showSetExactAlarmPermissionDialog by remember { mutableStateOf(false) }

    val (alarmTitle, alarmFinishMessage, alarmFiveMinutesBeforeFinish) =
        if (splashResultStateModel.recordTimes.recordingMode == 1) {
            Triple(
                stringResource(id = R.string.timer),
                stringResource(id = R.string.timer_finish_alarm_message),
                stringResource(id = R.string.timer_five_minutes_before_finish_alarm_message),
            )
        } else {
            Triple(
                stringResource(id = R.string.stopwatch),
                stringResource(id = R.string.stopwatch_alarm_message),
                null,
            )
        }

    val isFinishState by remember {
        derivedStateOf {
            val savedTime = uiState.measuringRecordTimes.savedTime
            val recordingMode = splashResultStateModel.recordTimes.recordingMode
            savedTime <= 0 && recordingMode == 1
        }
    }

    val stopMeasuring = {
        viewModel.stopMeasuring(
            recordTimes = uiState.recordTimes,
            measureTime = uiState.measureTime,
            endTime = ZonedDateTime.now(ZoneOffset.UTC).toString(),
        )
    }

    LaunchedEffect(Unit) {
        makeInProgressNotification(context)

        viewModel.setAlarm(
            title = alarmTitle,
            finishMessage = alarmFinishMessage,
            fiveMinutesBeforeFinish = alarmFiveMinutesBeforeFinish,
            measureTime = if (splashResultStateModel.recordTimes.recordingMode == 1) {
                splashResultStateModel.recordTimes.savedTimerTime - uiState.measureTime
            } else {
                splashResultStateModel.recordTimes.savedStopWatchTime + uiState.measureTime
            },
        )

        showSetExactAlarmPermissionDialog = !viewModel.canSetAlarm()
    }

    BackHandler {
        stopMeasuring()
        removeNotification(context)
        onFinish(uiState.measuringRecordTimes.savedTime <= 0L)
    }

    DisposableEffect(Unit) {
        onDispose {
            context.setBrightness(false)
        }
    }

    LaunchedEffect(uiState.isSleepMode) {
        context.setBrightness(uiState.isSleepMode)
    }

    LaunchedEffect(isFinishState) {
        if (isFinishState) {
            stopMeasuring()
            removeNotification(context)
            onFinish(uiState.measuringRecordTimes.savedTime <= 0L)
        }
    }

    if (showSetExactAlarmPermissionDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(id = R.string.alarm_permission_title),
                message = stringResource(id = R.string.alarm_permission_message),
                positiveText = stringResource(id = R.string.Ok),
                negativeText = stringResource(id = R.string.Cancel),
                onPositive = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(
                            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                            Uri.parse("package:" + context.packageName),
                        )
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                },
            ),
            onShowDialog = { showSetExactAlarmPermissionDialog = it },
            bodyContent = {},
        )
    }

    MeasuringScreen(
        uiState = uiState,
        onSleepClick = {
            viewModel.setSleepMode(!uiState.isSleepMode)
        },
        onFinishClick = {
            stopMeasuring()
            removeNotification(context)
            onFinish(uiState.measuringRecordTimes.savedTime <= 0L)
        },
    )
}

@Composable
private fun MeasuringScreen(
    uiState: MeasuringUiState,
    onSleepClick: () -> Unit,
    onFinishClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TdsIconButton(
                    modifier =
                    Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Start),
                    size = 32.dp,
                    onClick = onSleepClick,
                ) {
                    Icon(
                        painter =
                        if (uiState.isSleepMode) {
                            painterResource(id = R.drawable.sleep_icon)
                        } else {
                            painterResource(id = R.drawable.non_sleep_icon)
                        },
                        contentDescription = "sleepIcon",
                        tint = Color.White,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                TdsText(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = uiState.recordTimes.currentTask?.taskName,
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 18.sp,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(50.dp))

                with(uiState.measuringRecordTimes) {
                    TdsTimer(
                        outCircularLineColor = Color(uiState.measuringTimeColor.backgroundColor),
                        outCircularProgress = outCircularProgress,
                        inCircularLineTrackColor = TdsColor.WHITE,
                        inCircularProgress = inCircularProgress,
                        fontColor = TdsColor.WHITE,
                        themeColor = Color(uiState.measuringTimeColor.backgroundColor),
                        recordingMode = uiState.recordTimes.recordingMode,
                        savedSumTime = savedSumTime,
                        savedTime = savedTime,
                        savedGoalTime = savedGoalTime,
                        finishGoalTime = finishGoalTime,
                        isTaskTargetTimeOn = isTaskTargetTimeOn,
                        onClickStopStart = {
                            onFinishClick()
                        },
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                TdsIconButton(
                    onClick = onFinishClick,
                    size = 70.dp,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.stop_record_icon),
                        contentDescription = "startRecord",
                        tint = TdsColor.RED.getColor(),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        else -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .background(Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                with(uiState.measuringRecordTimes) {
                    TdsTimer(
                        outCircularLineColor = Color(uiState.measuringTimeColor.backgroundColor),
                        outCircularProgress = outCircularProgress,
                        inCircularLineTrackColor = TdsColor.WHITE,
                        inCircularProgress = inCircularProgress,
                        fontColor = TdsColor.WHITE,
                        themeColor = Color(uiState.measuringTimeColor.backgroundColor),
                        recordingMode = uiState.recordTimes.recordingMode,
                        savedSumTime = savedSumTime,
                        savedTime = savedTime,
                        savedGoalTime = savedGoalTime,
                        finishGoalTime = finishGoalTime,
                        isTaskTargetTimeOn = isTaskTargetTimeOn,
                        onClickStopStart = {
                            onFinishClick()
                        },
                    )
                }
            }
        }
    }
}

private fun makeInProgressNotification(context: Context) {
    val title = "TiTi"
    val message = "측정이 진행 중입니다."
    val channelId = "InProgressId"

    val deepLink = "titi://"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE,
    )

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_stat_name)
        .setContentTitle(title)
        .setContentText(message)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notificationManager.notify(1, builder.build())
}

private fun removeNotification(context: Context) {
    val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE,
    ) as NotificationManager
    notificationManager.cancel(0)
    notificationManager.cancel(1)
}
