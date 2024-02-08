package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsCircularProgressIndicator(
    modifier: Modifier = Modifier,
    sumTime: Int,
    maxTime: Int,
    color: Color,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val density = LocalDensity.current
        val minSize = min(maxWidth, maxHeight)
        val strokeWidth = minSize * 0.2
        val insideSize = minSize * 0.4
        val fontSize = with(density) { (insideSize / 2).toSp() }

        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = sumTime / maxTime.toFloat(),
            color = color,
            trackColor = color.copy(alpha = 0.5f),
            strokeCap = StrokeCap.Round,
            strokeWidth = strokeWidth,
        )

        TdsText(
            modifier = Modifier
                .width(insideSize)
                .wrapContentHeight(),
            text = sumTime.toString(),
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = fontSize,
            color = TdsColor.TEXT,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun TdsCircularProgressIndicatorPreview() {
    TiTiTheme {
        TdsCircularProgressIndicator(
            modifier = Modifier
                .size(110.dp)
                .background(TdsColor.BACKGROUND.getColor()),
            sumTime = 500,
            maxTime = 1000,
            color = TdsColor.D1.getColor(),
        )
    }
}
