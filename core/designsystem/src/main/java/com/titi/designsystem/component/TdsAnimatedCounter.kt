package com.titi.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TdsAnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int,
) {
    var oldCount by remember {
        mutableIntStateOf(count)
    }

    SideEffect {
        oldCount = count
    }

    Row(modifier = modifier) {
        val countString = count.toString().padStart(2,'0')
        val oldCountString = oldCount.toString().padStart(2,'0')
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
            ) { char ->
                Text(
                    text = char.toString(),
                )
            }
        }
    }
}
