package com.titi.feature.time.ui.measure

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.titi.core.util.addTimeToNow
import com.titi.designsystem.R
import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class MeasuringActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordTimes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(RECORD_TIMES_KEY, RecordTimes::class.java)
        } else {
            intent.getParcelableExtra(RECORD_TIMES_KEY) as? RecordTimes
        }

        val backgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(BACKGROUND_COLOR_KEY, TimeColor::class.java)
        } else {
            intent.getParcelableExtra(BACKGROUND_COLOR_KEY) as? TimeColor
        }

        setContent {
            TiTiTheme {
                if (recordTimes != null && backgroundColor != null) {
                    MeasuringScreen(
                        recordTimes = recordTimes,
                        themeColor = if (recordTimes.recordingMode == 1) {
                            Color(backgroundColor.timerBackgroundColor)
                        } else {
                            Color(backgroundColor.stopwatchBackgroundColor)
                        },
                        onFinishClick = {
                            finish()
                        }
                    )
                } else {
                    finish()
                }
            }
        }
    }

    companion object {
        const val RECORD_TIMES_KEY = "recordTimesKey"
        const val BACKGROUND_COLOR_KEY = "backgroundColorKey"
    }

}

@Composable
fun MeasuringScreen(
    recordTimes: RecordTimes,
    themeColor: Color,
    onFinishClick: () -> Unit,
) {
    val viewModel: MeasuringViewModel = mavericksActivityViewModel(
        argsFactory = {
            recordTimes.asMavericksArgs()
        }
    )

    val uiState by viewModel.collectAsState()

    MeasuringScreen(
        uiState = uiState,
        recordTimes = recordTimes,
        themeColor = themeColor,
        onSleepClick = {
            viewModel.setSleepMode(!uiState.isSleepMode)
        },
        onFinishClick = {
            viewModel.stopMeasuring(
                recordTimes = recordTimes,
                measureTime = uiState.measureTime,
                endTime = ZonedDateTime.now(ZoneOffset.UTC),
            )
            onFinishClick()
        }
    )
}

@Composable
private fun MeasuringScreen(
    uiState: MeasuringUiState,
    recordTimes: RecordTimes,
    themeColor: Color,
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
            text = recordTimes.currentTask?.taskName,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(50.dp))

        with(recordTimes) {
            TdsTimer(
                outCircularLineColor = themeColor,
                outCircularProgress = if (recordingMode == 1) {
                    if (uiState.isSleepMode) {
                        ((setTimerTime - savedTimerTime + uiState.measureTime) - (setTimerTime - savedTimerTime + uiState.measureTime) % 60) / setTimerTime.toFloat()
                    } else {
                        (setTimerTime - savedTimerTime + uiState.measureTime) / setTimerTime.toFloat()
                    }

                } else {
                    if (uiState.isSleepMode) {
                        ((savedStopWatchTime + uiState.measureTime) - (savedStopWatchTime + uiState.measureTime) % 60) / 3600f
                    } else {
                        (savedStopWatchTime + uiState.measureTime) / 3600f
                    }
                },
                inCircularLineTrackColor = TdsColor.whiteColor,
                inCircularProgress = if (uiState.isSleepMode) {
                    (savedSumTime + uiState.measureTime - (savedSumTime + uiState.measureTime) % 60) / setGoalTime.toFloat()
                } else {
                    (savedSumTime + uiState.measureTime) / setGoalTime.toFloat()
                },
                fontColor = TdsColor.whiteColor,
                themeColor = themeColor,
                recordingMode = recordingMode,
                savedSumTime = if (uiState.isSleepMode) {
                    (savedSumTime + uiState.measureTime) - (savedSumTime + uiState.measureTime) % 60
                } else {
                    savedSumTime + uiState.measureTime
                },
                savedTime = if (recordingMode == 1) {
                    if (uiState.isSleepMode) {
                        val total = savedTimerTime - uiState.measureTime
                        if (total % 60 == 0L) {
                            total
                        } else {
                            total - total % 60 + 60
                        }
                    } else {
                        savedTimerTime - uiState.measureTime
                    }
                } else {
                    if (uiState.isSleepMode) {
                        (savedStopWatchTime + uiState.measureTime) - (savedStopWatchTime + uiState.measureTime) % 60
                    } else {
                        savedStopWatchTime + uiState.measureTime
                    }
                },
                savedGoalTime = if (uiState.isSleepMode) {
                    val total = savedGoalTime - uiState.measureTime
                    total - total % 60
                } else {
                    savedGoalTime - uiState.measureTime
                },
                finishGoalTime = addTimeToNow(savedGoalTime - uiState.measureTime),
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