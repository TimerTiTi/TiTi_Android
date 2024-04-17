package com.titi.app.core.designsystem.component

import android.graphics.Picture
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.createCaptureImageModifier

@Composable
fun TdsStandardDailyGraph(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    totalTime: String,
    maxTime: String,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    taskData: List<TdsTaskData>,
    checked: Boolean,
    picture: Picture,
    onCheckedChange: (Boolean) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val size = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        OutlinedCard(
            modifier = Modifier
                .createCaptureImageModifier(picture = picture)
                .height(size + 20.dp)
                .width(size + 20.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(size * 0.07),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp),
            border = BorderStroke(
                width = 3.dp,
                TdsColor.SHADOW.getColor(),
            ),
        ) {
            Column(
                modifier = Modifier.padding(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    TdsText(
                        text = todayDate,
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = (size.value * 0.07).sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    TdsDayOfTheWeek(
                        modifier = Modifier.weight(1f).padding(bottom = 2.dp),
                        todayDayOfTheWeek = todayDayOfTheWeek,
                        color = tdsColors.first(),
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                TdsText(
                    text = "TimeLine",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = (size.value * 0.05).sp,
                    color = TdsColor.TEXT,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(size * 0.3)
                        .border(
                            width = 2.dp,
                            color = TdsColor.GRAPH_BORDER.getColor(),
                        )
                        .padding(2.dp),
                ) {
                    TdsTimeLineChart(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 4.dp,
                                start = 4.dp,
                                end = 4.dp,
                            ),
                        times = timeLines,
                        startColor = tdsColors[0].getColor(),
                        endColor = tdsColors[1].getColor(),
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    TdsTaskResultList(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(size * 0.6)
                            .border(
                                width = 2.dp,
                                color = TdsColor.GRAPH_BORDER.getColor(),
                            )
                            .padding(2.dp)
                            .padding(horizontal = 6.dp),
                        taskData = taskData,
                        isSpacing = true,
                        leftText = "✔",
                        height = 25.dp,
                        colors = tdsColors.map { it.getColor() },
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 2.dp,
                                color = TdsColor.GRAPH_BORDER.getColor(),
                            )
                            .padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))

                        TdsText(
                            text = "Total",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.04).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = totalTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = tdsColors.first(),
                        )

                        TdsText(
                            text = "Max",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.04).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsText(
                            text = maxTime,
                            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.06).sp,
                            color = tdsColors.first(),
                        )

                        TdsPieChart(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 3.dp),
                            taskData = taskData,
                            colors = tdsColors.map { it.getColor() },
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .offset(
                    x = -size / 2 + 26.dp,
                    y = -size * 0.38 + 33.dp,
                ),
        ) {
            TdsToggleIconButton(
                checkedIcon = R.drawable.checked_icon,
                uncheckedIcon = R.drawable.unchecked_icon,
                checked = checked,
                onCheckedChange = onCheckedChange,
                tint = TdsColor.TEXT,
            )
        }
    }
}

@Preview
@Composable
private fun TdsStandardDailyGraphPreview() {
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
        TdsStandardDailyGraph(
            modifier = Modifier.fillMaxWidth(),
            todayDate = "2024.02.04",
            todayDayOfTheWeek = 6,
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
            ),
            totalTime = "08:00:00",
            maxTime = "02:00:00",
            timeLines = listOf(
                3600,
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
            taskData = taskData,
            checked = false,
            picture = Picture(),
            onCheckedChange = {},
        )
    }
}
