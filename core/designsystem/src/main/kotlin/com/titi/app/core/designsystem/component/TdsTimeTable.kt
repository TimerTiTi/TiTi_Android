package com.titi.app.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeTable(
    modifier: Modifier = Modifier,
    timeTableData: List<TdsTimeTableData>,
    colors: List<Color>,
) {
    var fontSize by remember {
        mutableStateOf(7.sp)
    }
    val textStyle = TdsTextStyle
        .SEMI_BOLD_TEXT_STYLE
        .getTextStyle(fontSize = fontSize)
        .copy(color = TdsColor.TEXT.getColor())
    val hourTextMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawGrid()
            },
    ) {
        val itemWidth = size.width / 7
        val itemHeight = size.height / 24
        fontSize = (itemHeight / 5).sp

        repeat(24) { idx ->
            val hour = (idx + 6).let { if (it >= 24) it - 24 else it }.toString()
            val hourTextLayoutResult = hourTextMeasurer.measure(hour, textStyle)

            val startX = if (hour.length > 1) {
                itemWidth / 2 - hourTextLayoutResult.size.width
            } else {
                itemWidth / 2 - hourTextLayoutResult.size.width / 2
            }
            val startY = itemHeight * (idx + 0.5f) - hourTextLayoutResult.size.height / 2

            drawText(
                textMeasurer = hourTextMeasurer,
                text = hour,
                style = textStyle,
                topLeft = Offset(
                    x = startX,
                    y = startY,
                ),
            )
        }

        timeTableData.forEachIndexed { index, timeTableData ->
            val idx = (timeTableData.hour - 6).let { if (it < 0) it + 24 else it }
            val startX = itemWidth + itemWidth * 6 * timeTableData.start / 3600f
            val startY = itemHeight * idx
            val barWidth = itemWidth * 6 * (timeTableData.end - timeTableData.start) / 3600

            drawRoundRect(
                color = colors[index % colors.size],
                cornerRadius = CornerRadius(itemHeight / 5, itemHeight / 5),
                topLeft = Offset(
                    x = startX,
                    y = startY,
                ),
                size = Size(
                    width = barWidth,
                    height = itemHeight,
                ),
            )
        }
    }
}

fun DrawScope.drawGrid() {
    val itemWidth = size.width / 7
    val itemHeight = size.height / 24

    repeat(7 * 24) { idx ->
        val startX = (idx % 7) * itemWidth
        val startY = (idx / 7) * itemHeight

        drawRect(
            color = Color(0x80626262),
            topLeft = Offset(
                x = startX,
                y = startY,
            ),
            size = Size(itemWidth, itemHeight),
            style = Stroke(width = 1f),
        )
    }
}

@Preview
@Composable
private fun TdsTimeTablePreview() {
    TiTiTheme {
        TdsTimeTable(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            timeTableData = listOf(
                TdsTimeTableData(
                    hour = 3,
                    start = 1800,
                    end = 3600,
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
            colors = listOf(
                TdsColor.D1.getColor(),
                TdsColor.D2.getColor(),
                TdsColor.D3.getColor(),
                TdsColor.D4.getColor(),
                TdsColor.D5.getColor(),
                TdsColor.D6.getColor(),
                TdsColor.D7.getColor(),
            ),
        )
    }
}
