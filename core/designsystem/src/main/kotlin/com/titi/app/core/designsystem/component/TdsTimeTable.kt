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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTimeTable(modifier: Modifier = Modifier, data: List<TdsTimeTableData>) {
    var hour by remember {
        mutableStateOf("0")
    }
    var fontSize by remember {
        mutableStateOf(14.sp)
    }
    val textStyle = TdsTextStyle
        .SEMI_BOLD_TEXT_STYLE
        .getTextStyle(fontSize = fontSize)
        .copy(color = TdsColor.TEXT.getColor())
    val hourTextMeasurer = rememberTextMeasurer()
    val hourTextLayoutResult = remember(hour) {
        hourTextMeasurer.measure(hour, textStyle)
    }

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
            hour = (idx + 6).let { if (it >= 24) it - 24 else it }.toString()

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

        data.forEach { timeTableData ->
            val idx = (timeTableData.hour - 6).let { if (it < 0) it + 24 else it }
            val startX = itemWidth + itemWidth * 6 * timeTableData.start / 3600f
            val startY = itemHeight * idx
            val barWidth = itemWidth * 6 * (timeTableData.end - timeTableData.start) / 3600

            drawRoundRect(
                color = timeTableData.color,
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
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

    var startX = 0f
    var startY = 0f

    repeat(7 * 24) { idx ->
        drawRect(
            color = Color(0x80626262),
            topLeft = Offset(
                x = startX,
                y = startY,
            ),
            size = Size(itemWidth, itemHeight),
            style = Stroke(width = 3f),
        )
        startX += itemWidth

        if (startX == itemWidth * 7) {
            startX = 0f
            startY += itemHeight
        }
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
            data = listOf(
                TdsTimeTableData(
                    hour = 3,
                    start = 1800,
                    end = 2400,
                    color = TdsColor.D1.getColor(),
                ),
                TdsTimeTableData(
                    hour = 5,
                    start = 1234,
                    end = 2555,
                    color = TdsColor.D5.getColor(),
                ),
                TdsTimeTableData(
                    hour = 12,
                    start = 600,
                    end = 3444,
                    color = TdsColor.D6.getColor(),
                ),
                TdsTimeTableData(
                    hour = 23,
                    start = 2121,
                    end = 3333,
                    color = TdsColor.D12.getColor(),
                ),
            ),
        )
    }
}
