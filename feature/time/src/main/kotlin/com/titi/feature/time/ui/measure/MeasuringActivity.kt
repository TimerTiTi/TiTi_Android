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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                MeasuringScreen(
                    onFinishClick = {
                        finish()
                    }
                )
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
    onFinishClick : () -> Unit,
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
        Spacer(modifier = Modifier.height(32.dp))

        Spacer(modifier = Modifier.weight(1f))

        TdsText(
            modifier = Modifier.padding(vertical = 12.dp),
            text = "국어",
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(50.dp))

        TdsTimer(
            outCircularLineColor = TdsColor.redColor,
            outCircularProgress = 10f,
            inCircularLineTrackColor = TdsColor.whiteColor,
            inCircularProgress = 10f,
            fontColor = TdsColor.whiteColor,
            recordingMode = 1,
            savedSumTime = 1000L,
            savedTime = 1000L,
            savedGoalTime = 1000L,
            finishGoalTime = addTimeToNow(1000L),
        )

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

@Preview
@Composable
private fun MeasuringScreenPreview() {
    TiTiTheme {

    }
}