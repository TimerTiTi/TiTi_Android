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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsGraphContent
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.edit.model.EditUiState

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

    val uiState by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateCurrentDateDaily(uiState.currentDate)
    }

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(containerColor),
                ),
                title = {
                    TdsText(
                        text = currentDate.replace('-', '.'),
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
            uiState = uiState,
        )
    }
}

@Composable
private fun EditScreen(modifier: Modifier, uiState: EditUiState) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        with(uiState) {
            TdsGraphContent(
                modifier = Modifier.fillMaxWidth(),
                todayDate = currentDate.toString().replace('-', '.'),
                todayDayOfTheWeek = currentDate.dayOfWeek.value - 1,
                totalTime = dailyGraphData.totalTime,
                maxTime = dailyGraphData.maxTime,
                taskData = dailyGraphData.taskData,
                tdsColors = graphColors,
                timeLines = dailyGraphData.timeLine,
                timeTableData = dailyGraphData.tdsTimeTableData,
            )
        }
    }
}

@Preview
@Composable
private fun EditScreenPreview() {
    TiTiTheme {
        // EditScreen(currentDate = LocalDate.now()) { }
    }
}
