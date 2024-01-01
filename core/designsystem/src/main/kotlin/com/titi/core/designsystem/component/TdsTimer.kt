package com.titi.core.designsystem.component

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.window.layout.WindowMetricsCalculator
import com.titi.core.designsystem.extension.getTdsTime
import com.titi.core.designsystem.extension.times
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.designsystem.R
import kotlin.math.min

@Composable
fun TdsTimer(
    modifier: Modifier = Modifier,
    isFinish: Boolean = false,
    outCircularLineColor: Color,
    outCircularProgress: Float,
    inCircularLineTrackColor: TdsColor,
    inCircularProgress: Float,
    fontColor: TdsColor,
    themeColor: Color? = null,
    recordingMode: Int,
    savedSumTime: Long,
    savedTime: Long,
    savedGoalTime: Long,
    finishGoalTime: String,
    isTaskTargetTimeOn: Boolean
) {
    val activity = LocalContext.current as? Activity ?: return
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val widthDp = metrics.bounds.width() / activity.resources.displayMetrics.density
    val heightDp = metrics.bounds.height() / activity.resources.displayMetrics.density

    BoxWithConstraints(
        modifier = modifier.width(min(widthDp, heightDp).dp)
    ) {
        val minSize = min(maxHeight, maxWidth)
        val outCircularSize = minSize * 0.85
        val outCircularTrackWidth = minSize * 0.05
        val inCircularSize = outCircularSize - outCircularTrackWidth * 2
        val inCircularTrackWidth = outCircularTrackWidth * 0.4
        val contentSize = inCircularSize - inCircularTrackWidth * 2

        val subTextSize = minSize.value * 0.03
        val subTimerTextSize = minSize.value * 0.08
        val mainTextSize = minSize.value * 0.04
        val mainTimerTextSize = minSize.value * 0.175

        val outCircularAnimateProgress = animateFloatAsState(
            targetValue = outCircularProgress,
            label = "outCircularAnimateProgress",
            animationSpec = tween(1000)
        )
        val inCircularAnimateProgress = animateFloatAsState(
            targetValue = inCircularProgress,
            label = "inCircularAnimateProgress",
            animationSpec = tween(1000)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(outCircularSize)
                .align(Alignment.Center),
            progress = outCircularAnimateProgress.value,
            color = if (savedTime < 60 && recordingMode == 1) {
                TdsColor.redColor.getColor()
            } else {
                outCircularLineColor
            },
            trackColor = TdsColor.lightGrayColor.getColor(),
            strokeWidth = outCircularTrackWidth,
            strokeCap = StrokeCap.Round
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(inCircularSize)
                .align(Alignment.Center),
            progress = inCircularAnimateProgress.value,
            color = inCircularLineTrackColor.getColor(),
            trackColor = Color.Transparent,
            strokeWidth = inCircularTrackWidth,
            strokeCap = StrokeCap.Round
        )

        Column(
            modifier = Modifier
                .size(contentSize)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))

            TdsText(
                text = stringResource(R.string.sum_time),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(1f))

            TdsTimeCounter(
                modifier = Modifier.width(contentSize * 0.45),
                tdsTime = savedSumTime.getTdsTime(),
                color = fontColor.getColor(),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTimerTextSize.sp
            )

            Spacer(modifier = Modifier.weight(2f))

            TdsText(
                text = if (recordingMode == 1) {
                    stringResource(R.string.timer)
                } else {
                    stringResource(R.string.stopwatch)
                },
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = mainTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isFinish) {
                TdsText(
                    text = stringResource(id = R.string.finish_text),
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = mainTimerTextSize.sp,
                    color = fontColor.getColor()
                )
            } else {
                Row(
                    modifier = Modifier.width(contentSize * 0.9),
                ) {
                    if (savedTime < 0) {
                        TdsText(
                            text = "+",
                            textStyle = TdsTextStyle.normalTextStyle,
                            fontSize = mainTimerTextSize.sp,
                            color = themeColor ?: fontColor.getColor()
                        )
                    }

                    TdsTimeCounter(
                        tdsTime = savedTime.getTdsTime(),
                        color = themeColor ?: fontColor.getColor(),
                        textStyle = TdsTextStyle.normalTextStyle,
                        fontSize = mainTimerTextSize.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(2f))

            TdsText(
                text = if (isTaskTargetTimeOn) {
                    stringResource(id = R.string.task_time)
                } else {
                    stringResource(id = R.string.goal_time)
                },
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.width(contentSize * 0.45)) {
                if (savedGoalTime < 0) {
                    TdsText(
                        text = "+",
                        textStyle = TdsTextStyle.normalTextStyle,
                        fontSize = subTimerTextSize.sp,
                        color = fontColor.getColor()
                    )
                }
                TdsTimeCounter(
                    tdsTime = savedGoalTime.getTdsTime(),
                    color = fontColor.getColor(),
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = subTimerTextSize.sp
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            TdsText(
                text = "To $finishGoalTime",
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun TdsTimerPreview() {
    TiTiTheme {
        TdsTimer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            isFinish = true,
            outCircularLineColor = TdsColor.blueColor.getColor(),
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 1,
            savedSumTime = 11938,
            savedTime = -50,
            savedGoalTime = -1000,
            finishGoalTime = "11:57",
            isTaskTargetTimeOn = false
        )
    }
}

@Preview(widthDp = 800, heightDp = 800)
@Composable
private fun TdsTimerPreview1() {
    TiTiTheme {
        TdsTimer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            isFinish = false,
            outCircularLineColor = TdsColor.blueColor.getColor(),
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 1,
            savedSumTime = 11938,
            savedTime = 590,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
            isTaskTargetTimeOn = false
        )
    }
}

@Preview(widthDp = 1200, heightDp = 1200)
@Composable
private fun TdsTimerPreview2() {
    TiTiTheme {
        TdsTimer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            isFinish = false,
            outCircularLineColor = TdsColor.blueColor.getColor(),
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 2,
            savedSumTime = 11938,
            savedTime = 590,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
            isTaskTargetTimeOn = false
        )
    }
}

@Preview(widthDp = 1200, heightDp = 700)
@Composable
private fun TdsTimerPreview3() {
    TiTiTheme {
        TdsTimer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            isFinish = false,
            outCircularLineColor = TdsColor.blueColor.getColor(),
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 2,
            savedSumTime = 11938,
            savedTime = 690,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
            isTaskTargetTimeOn = false
        )
    }
}