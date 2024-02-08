package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsFilledCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    BoxWithConstraints(
        modifier = modifier.padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        Card(
            modifier = Modifier
                .width(width)
                .wrapContentHeight(),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = TdsColor.BACKGROUND.getColor(),
            ),
            elevation = CardDefaults.outlinedCardElevation(
                defaultElevation = 5.dp,
            ),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun TdsFilledCardPreview() {
    TiTiTheme {
        TdsFilledCard(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.size(100.dp))
        }
    }
}
