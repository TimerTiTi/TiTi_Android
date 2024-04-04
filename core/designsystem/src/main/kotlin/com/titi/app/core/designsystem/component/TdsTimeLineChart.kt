package com.titi.app.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeLineChart(
    modifier: Modifier = Modifier,
    times: List<Long>,
    startColor: Color,
    endColor: Color,
) {
    require(times.size == 24) {
        "The times list must be 24 in size"
    }

    val currentTimes by rememberUpdatedState(newValue = times)
    val currentStartColor by rememberUpdatedState(newValue = startColor)
    val currentEndColor by rememberUpdatedState(newValue = endColor)

    BoxWithConstraints(modifier = modifier) {
        val itemWidth = (maxWidth - 48.dp) / times.size

        Row(modifier = Modifier.fillMaxSize()) {
            currentTimes.forEachIndexed { index, time ->
                TdsTimeLineBar(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight(),
                    time = time,
                    hour = (index + 6).let { if (it >= 24) it - 24 else it }.toString(),
                    brush = Brush.verticalGradient(
                        listOf(
                            currentStartColor,
                            currentEndColor,
                        ),
                    ),
                )

                if (index != 23) {
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
    }
}

@Composable
private fun TdsTimeLineBar(modifier: Modifier = Modifier, time: Long, hour: String, brush: Brush) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TdsTextStyle
        .NORMAL_TEXT_STYLE
        .getTextStyle(fontSize = 10.sp)
        .copy(color = TdsColor.TEXT.getColor())
    val textLayoutResult = remember(hour) {
        textMeasurer.measure(hour, textStyle)
    }

    Canvas(modifier = modifier) {
        val spacing = 4.dp.toPx()

        val barWidth = size.width
        val barMaxHeight = size.height - spacing - textLayoutResult.size.height
        val barHeight = barMaxHeight * time / 3600
        val startY = size.height - barHeight - spacing - textLayoutResult.size.height
        val cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(
                            x = 0f,
                            y = startY,
                        ),
                        size = Size(barWidth, barHeight),
                    ),
                    topLeft = cornerRadius,
                    topRight = cornerRadius,
                ),
            )
        }

        drawPath(
            path = path,
            brush = brush,
        )

        drawText(
            textMeasurer = textMeasurer,
            text = hour,
            style = textStyle,
            topLeft = Offset(
                x = center.x - textLayoutResult.size.width / 2,
                y = size.height - textLayoutResult.size.height,
            ),
        )
    }
}

@Preview
@Composable
private fun TdsTimeLineBarPreview() {
    TiTiTheme {
        TdsTimeLineBar(
            modifier = Modifier
                .width(30.dp)
                .height(100.dp)
                .background(Color.White),
            time = 3600,
            hour = "24",
            brush = Brush
                .verticalGradient(
                    listOf(
                        TdsColor.D1.getColor(),
                        TdsColor.D2.getColor(),
                    ),
                ),
        )
    }
}

@Preview
@Composable
private fun TdsTimeLineChartPreview() {
    TiTiTheme {
        TdsTimeLineChart(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            times = listOf(
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
            startColor = TdsColor.D1.getColor(),
            endColor = TdsColor.D2.getColor(),
        )
    }
}
