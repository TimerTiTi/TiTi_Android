package com.titi.app.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    indicatorColor: Color = TdsColor.SEGMENT_INDICATOR.getColor(),
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
                .clip(RoundedCornerShape(9.dp))
                .background(color = TdsColor.SEGMENT_BACKGROUND.getColor()),
        ) {
            TdsTabRowIndicator(
                indicatorWidth = tabWidth,
                indicatorHeight = tabHeight,
                indicatorOffset = indicatorOffset,
                indicatorColor = indicatorColor,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, text ->
                    TdsTabRowItem(
                        onClick = {
                            onClick(index)
                        },
                        tabWidth = tabWidth,
                        text = text,
                        isDivider = index != selectedItemIndex &&
                            index != items.size - 1 &&
                            index != selectedItemIndex - 1,
                        dividerHeight = tabHeight / 2,
                    )
                }
            }
        }
    }
}

@Composable
private fun TdsTabRowItem(
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
    isDivider: Boolean,
    dividerHeight: Dp,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        TdsText(
            text = text,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 13.sp,
            color = TdsColor.TEXT,
        )

        if (isDivider) {
            Row(
                modifier = Modifier
                    .height(dividerHeight)
                    .align(Alignment.CenterEnd),
            ) {
                TdsDivider()
            }
        }
    }
}

@Composable
private fun TdsTabRowIndicator(
    indicatorWidth: Dp,
    indicatorHeight: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .offset(x = indicatorOffset)
            .height(indicatorHeight)
            .width(indicatorWidth)
            .padding(1.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(indicatorColor),
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TdsTabRowPreview() {
    TiTiTheme {
        TdsTabRow(
            modifier = Modifier
                .width(174.dp)
                .height(32.dp),
            selectedItemIndex = 0,
            items = listOf("Home", "Daily", "Week"),
            onClick = {},
        )
    }
}
