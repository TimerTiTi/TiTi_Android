package com.titi.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.titi.core.designsystem.extension.getTdsTime
import com.titi.core.designsystem.extension.times
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.designsystem.R

@Composable
fun TdsTimer(
    modifier: Modifier = Modifier,
    outCircularLineColor: TdsColor,
    outCircularProgress: Float,
    inCircularLineTrackColor: TdsColor,
    inCircularProgress: Float,
    fontColor : TdsColor,
    recordingMode: Int,
    savedSumTime: Long,
    savedTime: Long,
    savedGoalTime: Long,
    finishGoalTime: String,
) {
    BoxWithConstraints(modifier = modifier) {
        val minSize = min(maxHeight, maxWidth)
        val outCircularSize = minSize * 0.8
        val outCircularTrackWidth = minSize * 0.05
        val inCircularSize = outCircularSize - outCircularTrackWidth * 2
        val inCircularTrackWidth = outCircularTrackWidth * 0.4
        val contentSize = inCircularSize - inCircularTrackWidth * 2

        val subTextSize = minSize.value * 0.03
        val subTimerTextSize = minSize.value * 0.08
        val mainTextSize = minSize.value * 0.04
        val mainTimerTextSize = minSize.value * 0.175

        CircularProgressIndicator(
            modifier = Modifier
                .size(outCircularSize)
                .align(Alignment.Center),
            progress = outCircularProgress,
            color = outCircularLineColor.getColor(),
            trackColor = TdsColor.lightGrayColor.getColor(),
            strokeWidth = outCircularTrackWidth,
            strokeCap = StrokeCap.Round
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(inCircularSize)
                .align(Alignment.Center),
            progress = inCircularProgress,
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
                color = fontColor,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTimerTextSize.sp
            )

            Spacer(modifier = Modifier.weight(2f))

            TdsText(
                text = if (recordingMode == 1) stringResource(R.string.timer) else stringResource(R.string.stopwatch),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = mainTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(1f))

            TdsTimeCounter(
                modifier = Modifier.width(contentSize * 0.9),
                tdsTime = savedTime.getTdsTime(),
                color = fontColor,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = mainTimerTextSize.sp
            )

            Spacer(modifier = Modifier.weight(2f))

            TdsText(
                text = stringResource(R.string.goal_time),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTextSize.sp,
                color = fontColor
            )

            Spacer(modifier = Modifier.weight(1f))

            TdsTimeCounter(
                modifier = Modifier.width(contentSize * 0.45),
                tdsTime = savedGoalTime.getTdsTime(),
                color = fontColor,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = subTimerTextSize.sp
            )

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
            outCircularLineColor = TdsColor.blueColor,
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 1,
            savedSumTime = 11938,
            savedTime = 690,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
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
            outCircularLineColor = TdsColor.blueColor,
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 1,
            savedSumTime = 11938,
            savedTime = 590,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
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
            outCircularLineColor = TdsColor.blueColor,
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 2,
            savedSumTime = 11938,
            savedTime = 590,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
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
            outCircularLineColor = TdsColor.blueColor,
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.d3,
            inCircularProgress = 0.3f,
            fontColor = TdsColor.textColor,
            recordingMode = 2,
            savedSumTime = 11938,
            savedTime = 690,
            savedGoalTime = 2462,
            finishGoalTime = "11:57",
        )
    }
}