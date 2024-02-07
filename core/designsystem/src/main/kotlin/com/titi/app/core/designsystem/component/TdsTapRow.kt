package com.titi.app.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTabRow(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val tabWidth = maxWidth / items.size
        val tabHeight = maxHeight

        val indicatorOffset: Dp by animateDpAsState(
            targetValue = tabWidth * selectedItemIndex,
            animationSpec = tween(easing = LinearEasing),
            label = "indicatorOffset",
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(color = TdsColor.SEGMENT_BACKGROUND.getColor()),
        ) {
            TdsTabRowIndicator(
                indicatorWidth = tabWidth,
                indicatorHeight = tabHeight,
                indicatorOffset = indicatorOffset,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            ) {
                items.forEachIndexed { index, text ->
                    TdsTabRowItem(
                        onClick = {
                            onClick(index)
                        },
                        tabWidth = tabWidth,
                        text = text,
                    )

                    if (index != items.size - 1 && selectedItemIndex != index) {
                        Row(modifier = Modifier.height(tabHeight / 2)) {
                            TdsDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TdsTabRowItem(onClick: () -> Unit, tabWidth: Dp, text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        TdsText(
            text = text,
            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
            fontSize = 14.sp,
            color = TdsColor.TEXT,
        )
    }
}

@Composable
private fun TdsTabRowIndicator(indicatorWidth: Dp, indicatorHeight: Dp, indicatorOffset: Dp) {
    Box(
        modifier = Modifier
            .offset(x = indicatorOffset)
            .clip(RoundedCornerShape(4.dp))
            .height(indicatorHeight)
            .width(indicatorWidth)
            .background(TdsColor.SEGMENT_INDICATIOR.getColor())
            .border(
                width = 1.dp,
                color = TdsColor.DIVIDER.getColor(),
                shape = RoundedCornerShape(4.dp),
            ),

    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TdsTabRowPreview() {
    TiTiTheme {
        TdsTabRow(
            modifier = Modifier
                .width(150.dp)
                .height(30.dp),
            selectedItemIndex = 0,
            items = listOf("Home", "Daily", "Week"),
            onClick = {},
        )
    }
}
