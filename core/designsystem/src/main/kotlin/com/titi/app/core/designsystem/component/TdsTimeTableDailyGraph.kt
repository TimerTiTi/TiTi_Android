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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.createCaptureImageModifier

@Composable
fun TdsTimeTableDailyGraph(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    totalTime: String,
    maxTime: String,
    tdsColors: List<TdsColor>,
    taskData: List<TdsTaskData>,
    timeTableData: List<TdsTimeTableData>,
    checked: Boolean? = null,
    picture: Picture? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    selectedTaskIndex: Int? = null,
    onClickTask: ((taskName: String, index: Int) -> Unit)? = null,
    onClickAddTask: (() -> Unit)? = null,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val size = maxWidth.coerceAtMost(365.dp)

        OutlinedCard(
            modifier = Modifier
                .createCaptureImageModifier(picture = picture)
                .size(size)
                .padding(10.dp),
            shape = RoundedCornerShape(size * 0.07),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
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
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 2.dp),
                        todayDayOfTheWeek = todayDayOfTheWeek,
                        color = tdsColors.first(),
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .width(size * 0.6)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TdsText(
                            text = "Times",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.05).sp,
                            color = TdsColor.TEXT,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(size * 0.3)
                                .border(
                                    width = 2.dp,
                                    color = TdsColor.GRAPH_BORDER.getColor(),
                                )
                                .padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TdsPieChart(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 10.dp),
                                taskData = taskData,
                                colors = tdsColors.map { it.getColor() },
                            )

                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(end = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
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
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        TdsTaskResultList(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = if (selectedTaskIndex == -1) {
                                        TdsColor.RED.getColor()
                                    } else {
                                        TdsColor.GRAPH_BORDER.getColor()
                                    },
                                )
                                .padding(2.dp),
                            taskData = taskData,
                            colors = tdsColors.map { it.getColor() },
                            isSpacing = true,
                            leftText = "✔",
                            height = 25.dp,
                            selectedIndex = selectedTaskIndex,
                            onClickTask = onClickTask,
                            onClickAddTask = onClickAddTask,
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier
                            .width(size * 0.30)
                            .height(size * 0.84),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TdsText(
                            text = "TimeTable",
                            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                            fontSize = (size.value * 0.05).sp,
                            color = TdsColor.TEXT,
                        )

                        TdsTimeTable(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = TdsColor.GRAPH_BORDER.getColor(),
                                )
                                .padding(2.dp),
                            timeTableData = timeTableData,
                            colors = tdsColors.map { it.getColor() },
                        )
                    }
                }
            }
        }

        onCheckedChange?.let {
            Box(
                modifier = Modifier
                    .offset(
                        x = -size / 2 + 36.dp,
                        y = -size * 0.38 + 43.dp,
                    ),
            ) {
                TdsToggleIconButton(
                    checkedIcon = R.drawable.checked_icon,
                    uncheckedIcon = R.drawable.unchecked_icon,
                    checked = checked ?: false,
                    onCheckedChange = onCheckedChange,
                    tint = TdsColor.TEXT,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TdsTimeTableDailyGraphPreview() {
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
        TdsTimeTableDailyGraph(
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
            totalTime = "10:00:00",
            maxTime = "02:00:00",
            taskData = taskData,
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
            checked = false,
            picture = Picture(),
            onCheckedChange = {},
        )
    }
}
