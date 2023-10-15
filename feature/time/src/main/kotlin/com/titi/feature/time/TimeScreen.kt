package com.titi.feature.time

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.component.TdsTimer
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.designsystem.R

@Composable
fun TimeScreen(
    viewModel : TimeViewModel = mavericksViewModel(),
    backgroundColor: TdsColor,
    recordingMode: Int,
) {
    val uiState by viewModel.collectAsState()

    TimeScreen(
        backgroundColor = backgroundColor,
        recordingMode = recordingMode,
        taskName = null,
        onClickAddRecord = {},
        onClickStartRecord = {},
        onClickSettingTime = {},
    )
}

@Composable
private fun TimeScreen(
    backgroundColor: TdsColor,
    recordingMode: Int,
    taskName: String?,
    onClickAddRecord: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickSettingTime: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.getColor())
            .padding(top = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            //TODO 날짜 입력받고, 상태에 따른 컬러값
            TdsText(
                modifier = Modifier.align(Alignment.Center),
                text = "2023.01.01",
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 16.sp,
                color = TdsColor.textColor
            )

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.cancel_icon),
                contentDescription = "asdf"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (taskName == null) {
            TdsText(
                modifier = Modifier
                    .border(
                        width = 3.dp,
                        color = TdsColor.redColor.getColor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        vertical = 10.dp,
                        horizontal = 25.dp
                    ),
                text = stringResource(R.string.create_task_text),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 18.sp,
                color = TdsColor.redColor
            )
        } else {
            TdsText(
                modifier = Modifier
                    .border(
                        width = 3.dp,
                        color = TdsColor.textColor.getColor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        vertical = 10.dp,
                        horizontal = 25.dp
                    ),
                text = taskName,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 18.sp,
                color = TdsColor.textColor
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        //TODO 타이머 색상 커스텀
        //TODO 기록 가져오기
        TdsTimer(
            outCircularLineColor = TdsColor.d3,
            outCircularProgress = 0.3f,
            inCircularLineTrackColor = TdsColor.whiteColor,
            inCircularProgress = 0.2f,
            fontColor = TdsColor.textColor,
            recordingMode = recordingMode,
            savedSumTime = 3000L,
            savedTime = 3000L,
            savedGoalTime = 3000L,
            finishGoalTime = "05:30 PM",
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.clickable { onClickAddRecord() },
                painter = painterResource(id = R.drawable.add_record_icon),
                contentDescription = "addRecord"
            )

            Spacer(modifier = Modifier.width(25.dp))

            Icon(
                modifier = Modifier.clickable { onClickStartRecord() },
                painter = painterResource(id = R.drawable.start_record_icon),
                contentDescription = "startRecord",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(25.dp))

            Icon(
                modifier = Modifier.clickable { onClickSettingTime() },
                painter = painterResource(
                    id = if (recordingMode == 1) {
                        R.drawable.setting_timer_time_icon
                    } else {
                        R.drawable.setting_stopwatch_time_icon
                    }
                ),
                contentDescription = "addRecord"
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview
@Composable
private fun TimeScreenPreview() {
    TiTiTheme {
        TimeScreen(
            backgroundColor = TdsColor.blueColor,
            recordingMode = 1,
            taskName = null,
            onClickAddRecord = {},
            onClickStartRecord = {},
            onClickSettingTime = {},
        )
    }
}