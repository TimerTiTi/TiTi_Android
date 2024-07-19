package com.titi.app.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
internal fun TdsAnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int,
    color: Color,
    textStyle: TdsTextStyle,
    fontSize: TextUnit,
) {
    var oldCount by remember {
        mutableIntStateOf(count)
    }

    SideEffect {
        oldCount = count
    }

    Row(modifier = modifier) {
        val countString = count.toString().padStart(2, '0')
        val oldCountString = oldCount.toString().padStart(2, '0')
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }

            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = "",
            ) { character ->
                TdsText(
                    text = character.toString(),
                    textStyle = textStyle,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                    color = color,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TdsAnimatedCounterPreview() {
    TiTiTheme {
        TdsAnimatedCounter(
            count = 13,
            color = TdsColor.TEXT.getColor(),
            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
            fontSize = 40.sp,
        )
    }
}
