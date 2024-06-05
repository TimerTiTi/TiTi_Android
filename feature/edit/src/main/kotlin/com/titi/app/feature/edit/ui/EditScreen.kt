package com.titi.app.feature.edit.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsGraphContent
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(currentDate: String, onBack: () -> Unit) {
    val viewModel: EditViewModel = mavericksViewModel(
        argsFactory = {
            currentDate.asMavericksArgs()
        },
    )
    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFFFFFFF
    }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(containerColor),
                ),
                title = {
                    TdsText(
                        text = currentDate.toString().replace('-', '.'),
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )
                },
                navigationIcon = {
                    TdsIconButton(onClick = onBack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = "back",
                            tint = TdsColor.TEXT.getColor(),
                        )
                    }
                },
                actions = {
                    TdsText(
                        text = "SAVE",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                },
            )
        },
    ) {
        EditScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState),
        )
    }
}

@Composable
private fun EditScreen(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        TdsGraphContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = "2024.02.04",
            todayDayOfTheWeek = 6,
            totalTime = "10:00:00",
            maxTime = "03:00:00",
            taskData = listOf(
                TdsTaskData(
                    key = "수업",
                    value = "02:00:00",
                    progress = 0.2f,
                ),
                TdsTaskData(
                    key = "인공지능",
                    value = "03:00:00",
                    progress = 0.3f,
                ),
                TdsTaskData(
                    key = "알고리즘",
                    value = "02:00:00",
                    progress = 0.2f,
                ),
                TdsTaskData(
                    key = "개발",
                    value = "03:00:00",
                    progress = 0.3f,
                ),
            ),
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
                TdsColor.D8,
                TdsColor.D9,
                TdsColor.D11,
                TdsColor.D12,
            ),
            timeLines = listOf(
                3600L,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
                800,
                1200,
                300,
                400,
                100,
                600,
            ),
            timeTableData = listOf(
                TdsTimeTableData(
                    hour = 3,
                    start = 1800,
                    end = 2400,
                ),
                TdsTimeTableData(
                    hour = 5,
                    start = 1234,
                    end = 2555,
                ),
                TdsTimeTableData(
                    hour = 12,
                    start = 600,
                    end = 3444,
                ),
                TdsTimeTableData(
                    hour = 23,
                    start = 2121,
                    end = 3333,
                ),
            ),
        )
    }
}

@Preview
@Composable
private fun EditScreenPreview() {
    TiTiTheme {
        // EditScreen(currentDate = LocalDate.now()) { }
    }
}
