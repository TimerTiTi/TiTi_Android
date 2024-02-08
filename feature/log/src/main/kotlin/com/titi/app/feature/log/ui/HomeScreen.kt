package com.titi.app.feature.log.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.component.TdsCard
import com.titi.app.core.designsystem.component.TdsCircularProgressIndicator
import com.titi.app.core.designsystem.component.TdsFilledCard
import com.titi.app.core.designsystem.component.TdsTaskResultList
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun HomeScreen(tdsColors: List<TdsColor>, taskData: List<TdsTaskData>) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TotalCard(
            tdsColors = tdsColors,
            taskData = taskData,
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

            Row(
                modifier = Modifier.width(width),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MonthSumCard(themeColor = tdsColors.first())

                WeekSumCard(themeColor = tdsColors.first())
            }
        }
    }
}

@Composable
private fun TotalCard(tdsColors: List<TdsColor>, taskData: List<TdsTaskData>) {
    TdsFilledCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            TdsCircularProgressIndicator(
                modifier = Modifier.size(110.dp),
                sumTime = 10,
                maxTime = 100,
                color = tdsColors.first().getColor(),
            )

            Spacer(modifier = Modifier.width(15.dp))

            TdsTaskResultList(
                modifier = Modifier.weight(1f),
                taskData = taskData,
                colors = tdsColors.map { it.getColor() },
                isSpacing = true,
                height = 20.dp,
                leftText = "Top",
            )
        }
    }
}

@Composable
private fun MonthSumCard(themeColor: TdsColor) {
    TdsCard(
        modifier = Modifier
            .width(158.dp)
            .height(158.dp),
    ) {
        TdsCircularProgressIndicator(
            modifier = Modifier.size(110.dp),
            sumTime = 10,
            maxTime = 100,
            color = themeColor.getColor(),
        )
    }
}

@Composable
private fun WeekSumCard(themeColor: TdsColor) {
    TdsCard(
        modifier = Modifier
            .width(158.dp)
            .height(158.dp),
    ) {
        TdsCircularProgressIndicator(
            modifier = Modifier.size(110.dp),
            sumTime = 20,
            maxTime = 100,
            color = themeColor.getColor(),
        )
    }
}

@Composable
private fun WeekCard() {
}

@Composable
private fun TimeLineCard() {
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val tdsColors = listOf(
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
    )

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
        TdsTaskData(
            key = "개발",
            value = "03:00:00",
            progress = 0.3f,
        ),
    )

    TiTiTheme {
        HomeScreen(
            tdsColors = tdsColors,
            taskData = taskData,
        )
    }
}
