package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTaskProgressDailyGraph(
    todayDate: String,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
) {
    Box(
        modifier = Modifier.size(365.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .size(345.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 10.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(15.dp))

                TdsText(
                    text = todayDate,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = 25.sp,
                    color = TdsColor.TEXT,
                )

                TdsPieChart(
                    modifier = Modifier.fillMaxSize(),
                    taskData = taskData,
                    colors = tdsColors.map { it.getColor() },
                    containsDonut = true,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TdsTaskProgressDailyGraphPreview() {
    val taskData = listOf(
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
    )

    TiTiTheme {
        TdsTaskProgressDailyGraph(
            todayDate = "2024.02.04",
            taskData = taskData,
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
            ),
        )
    }
}
