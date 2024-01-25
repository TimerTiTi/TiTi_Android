package com.titi.app.core.designsystem.component.piechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieData: List<TdsPieData>,
    holeRadiusPercent: Float = 0.5f,
    animationSpec: AnimationSpec<Float> = TweenSpec(durationMillis = 500),
) {
    val transitionProgress = remember(pieData) {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(pieData) {
        transitionProgress.animateTo(
            targetValue = 1f,
            animationSpec = animationSpec,
        )
    }

    TdsPieChart(
        modifier = modifier,
        pieData = pieData,
        holeRadiusPercent = holeRadiusPercent,
        progress = transitionProgress.value,
    )
}

@Composable
private fun TdsPieChart(
    modifier: Modifier = Modifier,
    pieData: List<TdsPieData>,
    holeRadiusPercent: Float = 0.5f,
    progress: Float,
) {
    var startAngle = 0f

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 3
        val centerX = size.width / 2
        val centerY = size.height / 2
        val holeRadius = radius * holeRadiusPercent

        pieData.forEach { pie ->
            val sweepAngle = (pie.progress * 360 - 3) * progress

            drawArc(
                color = pie.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = radius - holeRadius),
            )

            startAngle += sweepAngle

            drawArc(
                color = Color.Black,
                startAngle = startAngle,
                sweepAngle = 3 * progress,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = radius - holeRadius),
            )

            startAngle += 3
        }
    }
}

@Preview(widthDp = 80, heightDp = 80)
@Composable
private fun TdsPieChartPreview() {
    TiTiTheme {
        PieChart(
            modifier = Modifier.fillMaxSize(),
            holeRadiusPercent = 0f,
            pieData = listOf(
                TdsPieData(
                    key = "수업",
                    value = "2:00:00",
                    progress = 0.2f,
                    color = Color.Blue,
                ),
                TdsPieData(
                    key = "인공지능",
                    value = "3:00:00",
                    progress = 0.3f,
                    color = Color.Red,
                ),
                TdsPieData(
                    key = "알고리즘",
                    value = "2:00:00",
                    progress = 0.2f,
                    color = Color.Gray,
                ),
                TdsPieData(
                    key = "개발",
                    value = "3:00:00",
                    progress = 0.3f,
                    color = Color.Cyan,
                ),
            ),
        )
    }
}