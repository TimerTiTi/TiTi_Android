package com.titi.app.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import kotlin.math.floor

@Composable
fun TdsTimeLineChart(
    modifier: Modifier = Modifier,
    isCircleDraw: Boolean = false,
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
        val itemWidth = floor(maxWidth.value / times.size).dp

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            currentTimes.forEachIndexed { index, time ->
                Column(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight(),
                ) {
                    if (isCircleDraw) {
                        Canvas(modifier = Modifier.size(itemWidth)) {
                            drawCircle(
                                color = currentStartColor.copy(
                                    alpha = (time / 3600f).coerceAtMost(1f),
                                ),
                                radius = itemWidth.toPx() * 0.45f,
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    TdsTimeLineBar(
                        modifier = Modifier.fillMaxSize(),
                        time = time,
                        hour = (index + 6).let { if (it >= 24) it - 24 else it }.toString(),
                        brush = Brush.verticalGradient(
                            listOf(
                                currentStartColor,
                                currentEndColor,
                            ),
                        ),
                    )
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

        val radius = size.width * 0.5f
        val barWidth = size.width * 0.9f
        val barMaxHeight = size.height - spacing - textLayoutResult.size.height
        val barHeight = barMaxHeight * time / 3600
        val barSpacing = size.width * 0.05f
        val startY = size.height - barHeight - spacing - textLayoutResult.size.height
        val cornerRadius = CornerRadius(radius, radius)
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(
                            x = barSpacing,
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
private fun TdsTimeLineChartPreview() {
    TiTiTheme {
        TdsTimeLineChart(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            isCircleDraw = true,
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
