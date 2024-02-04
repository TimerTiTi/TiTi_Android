package com.titi.app.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.min
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    taskData: List<TdsTaskData>,
    colors: List<Color>,
    containsDonut: Boolean = false,
    animationSpec: AnimationSpec<Float> = TweenSpec(durationMillis = 500),
) {
    val transitionProgress = remember(taskData) {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(taskData) {
        transitionProgress.animateTo(
            targetValue = 1f,
            animationSpec = animationSpec,
        )
    }

    TdsPieChart(
        modifier = modifier,
        pieData = taskData,
        colors = colors,
        progress = transitionProgress.value,
        containsDonut = containsDonut,
    )
}

@Composable
private fun TdsPieChart(
    modifier: Modifier = Modifier,
    pieData: List<TdsTaskData>,
    colors: List<Color>,
    progress: Float,
    containsDonut: Boolean = false,
) {
    var startAngle = 0f
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val radius = with(density) { (min(maxWidth, maxHeight) / 3).toPx() }
        val centerX = with(density) { (maxWidth / 2).toPx() }
        val centerY = with(density) { (maxHeight / 2).toPx() }
        val holeRadiusPercent = if (containsDonut) {
            0.5f
        } else {
            0f
        }
        val holeRadius = radius * holeRadiusPercent
        val holeRadiusDp = with(density) { holeRadius.toDp() }

        Canvas(modifier = Modifier.fillMaxSize()) {
            pieData.forEachIndexed { index, pie ->
                val sweepAngle = (pie.progress * 360 - 3) * progress

                drawArc(
                    color = colors[index % colors.size],
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

        if (containsDonut) {
            TdsTaskResultList(
                modifier = Modifier.size(holeRadiusDp * 2),
                pieData = pieData,
                isSpacing = false,
                isCheck = false,
                height = holeRadiusDp * 2 / 5,
                colors = colors,
            )
        }
    }
}

@Preview
@Composable
private fun TdsPieChartPreview() {
    TiTiTheme {
        PieChart(
            modifier = Modifier.fillMaxSize(),
            containsDonut = true,
            taskData = listOf(
                TdsTaskData(
                    key = "수업",
                    value = "2:00:00",
                    progress = 0.2f,
                ),
                TdsTaskData(
                    key = "인공지능",
                    value = "3:00:00",
                    progress = 0.3f,
                ),
                TdsTaskData(
                    key = "알고리즘",
                    value = "2:00:00",
                    progress = 0.2f,
                ),
                TdsTaskData(
                    key = "개발",
                    value = "3:00:00",
                    progress = 0.3f,
                ),
            ),
            colors = listOf(
                TdsColor.D1.getColor(),
                TdsColor.D2.getColor(),
                TdsColor.D3.getColor(),
                TdsColor.D4.getColor(),
                TdsColor.D5.getColor(),
                TdsColor.D6.getColor(),
            ),
        )
    }
}
