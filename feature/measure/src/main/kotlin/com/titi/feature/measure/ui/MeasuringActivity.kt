package com.titi.feature.measure.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.titi.core.designsystem.component.TdsIconButton
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.component.TdsTimer
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.core.ui.TiTiDeepLinkArgs.MEASURE_ARGS
import com.titi.core.util.fromJson
import com.titi.core.util.toJson
import com.titi.designsystem.R
import com.titi.feature.measure.SplashResultState
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class MeasuringActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashResultState =
            intent.data?.getQueryParameter(MEASURE_ARGS)?.fromJson<SplashResultState>()

        val resultIntent = Intent()

        setContent {
            TiTiTheme {
                splashResultState?.let {
                    MeasuringScreen(
                        splashResultState = it,
                        onFinish = {
                            resultIntent.putExtra(MEASURE_ARGS, it.toJson())
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
    splashResultState: SplashResultState,
    onFinish: () -> Unit,
) {
    val viewModel: MeasuringViewModel = mavericksActivityViewModel(
        argsFactory = {
            splashResultState.asMavericksArgs()
        }
    )

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    val uiState by viewModel.collectAsState()
    val context = LocalContext.current

    BackHandler {
        viewModel.stopMeasuring(
            recordTimes = uiState.recordTimes,
            measureTime = uiState.measureTime,
            endTime = ZonedDateTime.now(ZoneOffset.UTC).toString(),
        )
        onFinish()
    }

    DisposableEffect(Unit) {
        onDispose {
            setBrightness(context, isSleepMode = false)
        }
    }

    LaunchedEffect(uiState.isSleepMode) {
        setBrightness(
            context = context,
            isSleepMode = uiState.isSleepMode
        )
    }

    MeasuringScreen(
        uiState = uiState,
        onSleepClick = {
            viewModel.setSleepMode(!uiState.isSleepMode)
        },
        onFinishClick = {
            viewModel.stopMeasuring(
                recordTimes = uiState.recordTimes,
                measureTime = uiState.measureTime,
                endTime = ZonedDateTime.now(ZoneOffset.UTC).toString(),
            )
            onFinish()
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

fun setBrightness(context: Context, isSleepMode: Boolean) {
    val activity = context as? Activity ?: return
    val layoutParams: WindowManager.LayoutParams = activity.window.attributes
    layoutParams.screenBrightness =
        if (isSleepMode) 5f / 255 else WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
    activity.window.attributes = layoutParams
}