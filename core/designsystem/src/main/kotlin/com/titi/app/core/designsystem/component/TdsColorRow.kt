package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsColorRow(modifier: Modifier = Modifier, onClick: (Int) -> Unit) {
    val tdsColors = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D10,
        TdsColor.D11,
        TdsColor.D12,
    )

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val size = maxWidth.coerceAtMost(345.dp)
        val width = size / 16

        Row(
            modifier = Modifier.width(size),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            tdsColors.forEachIndexed { index, tdsColor ->
                Box(
                    modifier = Modifier
                        .size(width)
                        .clip(RoundedCornerShape(width / 8))
                        .background(tdsColor.getColor())
                        .clickable { onClick(index) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun TdsColorRowPreview() {
    TiTiTheme {
        TdsColorRow(modifier = Modifier.fillMaxWidth()) {
        }
    }
}
