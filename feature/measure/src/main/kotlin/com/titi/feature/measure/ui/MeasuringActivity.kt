package com.titi.feature.measure.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimer
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.ui.TiTiArgs.MAIN_FINISH_ARG
import com.titi.app.core.ui.TiTiArgs.MAIN_START_ARG
import com.titi.app.core.ui.TiTiDeepLinkArgs.MEASURE_ARG
import com.titi.app.core.ui.setBrightness
import com.titi.core.util.fromJson
import com.titi.app.core.designsystem.R
import com.titi.domain.time.model.RecordTimes
import com.titi.feature.measure.SplashResultState
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class MeasuringActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashResultState =
            intent.data?.getQueryParameter(MEASURE_ARG)?.fromJson<SplashResultState>()

        setContent {
            TiTiTheme {
                splashResultState?.let { safeSplashResultState ->
                    val viewModel: MeasuringViewModel = mavericksActivityViewModel(
                        argsFactory = {
                            safeSplashResultState.asMavericksArgs()
                        }
                    )

                    MeasuringScreen(
                        viewModel = viewModel,
                        splashResultState = safeSplashResultState,
                        onFinish = { recordTimes: RecordTimes, measureTime: Long, isFinish: Boolean ->
                            viewModel.stopMeasuring(
                                recordTimes = recordTimes,
                                measureTime = measureTime,
                                endTime = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                            )

                            val resultIntent = Intent()
                            resultIntent.putExtra(MAIN_FINISH_ARG, isFinish)
                            resultIntent.putExtra(
                                MAIN_START_ARG,
                                safeSplashResultState.recordTimes.recordingMode
                            )
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        }
                    )
                } ?: finish()
            }
        }
    }

}

@Composable
fun MeasuringScreen(
    viewModel: MeasuringViewModel,
    splashResultState: SplashResultState,
    onFinish: (recordTimes: RecordTimes, measureTime: Long, isFinish: Boolean) -> Unit,
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current
    var showSetExactAlarmPermissionDialog by remember { mutableStateOf(false) }

    val (alarmTitle, alarmFinishMessage, alarmFiveMinutesBeforeFinish) = if (splashResultState.recordTimes.recordingMode == 1) {
        Triple(
            stringResource(id = R.string.timer),
            stringResource(id = R.string.timer_finish_alarm_message),
            stringResource(id = R.string.timer_five_minutes_before_finish_alarm_message)
        )
    } else {
        Triple(
            stringResource(id = R.string.stopwatch),
            stringResource(id = R.string.stopwatch_alarm_message),
            null
        )
    }

    val isFinishState by remember {
        derivedStateOf {
            uiState.measuringRecordTimes.savedTime <= 0 && splashResultState.recordTimes.recordingMode == 1
        }
    }

    LaunchedEffect(Unit) {
        viewModel.start()

        viewModel.setAlarm(
            title = alarmTitle,
            finishMessage = alarmFinishMessage,
            fiveMinutesBeforeFinish = alarmFiveMinutesBeforeFinish,
            measureTime = if (splashResultState.recordTimes.recordingMode == 1) {
                splashResultState.recordTimes.savedTimerTime
            } else {
                splashResultState.recordTimes.savedStopWatchTime
            }
        )

        showSetExactAlarmPermissionDialog = !viewModel.canSetAlarm()
    }

    BackHandler {
        onFinish(
            uiState.recordTimes,
            uiState.measureTime,
            uiState.measuringRecordTimes.savedTime <= 0L
        )
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
            onFinish(
                uiState.recordTimes,
                uiState.measureTime,
                true
            )
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
                            ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                            Uri.parse("package:" + context.packageName)
                        )
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }
            ),
            onShowDialog = { showSetExactAlarmPermissionDialog = it },
            bodyContent = {}
        )
    }

    MeasuringScreen(
        uiState = uiState,
        onSleepClick = {
            viewModel.setSleepMode(!uiState.isSleepMode)
        },
        onFinishClick = {
            onFinish(
                uiState.recordTimes,
                uiState.measureTime,
                uiState.measuringRecordTimes.savedTime <= 0L
            )
        }
    )
}

@Composable
private fun MeasuringScreen(
    uiState: MeasuringUiState,
    onSleepClick: () -> Unit,
    onFinishClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TdsIconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.Start),
            size = 32.dp,
            onClick = onSleepClick,
        ) {
            Icon(
                painter = if (uiState.isSleepMode) {
                    painterResource(id = R.drawable.sleep_icon)
                } else {
                    painterResource(id = R.drawable.non_sleep_icon)
                },
                contentDescription = "sleepIcon",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TdsText(
            modifier = Modifier.padding(vertical = 12.dp),
            text = uiState.recordTimes.currentTask?.taskName,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(50.dp))

        with(uiState.measuringRecordTimes) {
            TdsTimer(
                outCircularLineColor = Color(uiState.measuringTimeColor.backgroundColor),
                outCircularProgress = outCircularProgress,
                inCircularLineTrackColor = TdsColor.whiteColor,
                inCircularProgress = inCircularProgress,
                fontColor = TdsColor.whiteColor,
                themeColor = Color(uiState.measuringTimeColor.backgroundColor),
                recordingMode = uiState.recordTimes.recordingMode,
                savedSumTime = savedSumTime,
                savedTime = savedTime,
                savedGoalTime = savedGoalTime,
                finishGoalTime = finishGoalTime,
                isTaskTargetTimeOn = isTaskTargetTimeOn
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        TdsIconButton(
            onClick = onFinishClick,
            size = 70.dp
        ) {
            Icon(
                painter = painterResource(id = R.drawable.stop_record_icon),
                contentDescription = "startRecord",
                tint = TdsColor.redColor.getColor()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.height(80.dp))
    }
}
