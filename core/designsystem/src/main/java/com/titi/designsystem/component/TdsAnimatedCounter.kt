package com.titi.designsystem.component

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.designsystem.theme.TdsColor
import com.titi.designsystem.theme.TdsTextStyle
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsAnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int,
    color: TdsColor,
    textStyle: TdsTextStyle,
    fontSize : TextUnit,
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
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = ""
            ) { character ->
                TdsText(
                    text = character.toString(),
                    textStyle = textStyle,
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
            color = TdsColor.blackColor,
            textStyle = TdsTextStyle.blackTextStyle,
            fontSize = 40.sp
        )
    }
}
