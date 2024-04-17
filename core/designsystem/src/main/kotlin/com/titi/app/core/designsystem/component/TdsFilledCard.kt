package com.titi.app.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsFilledCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = TdsColor.BACKGROUND.getColor(),
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 5.dp,
        ),
        border = BorderStroke(
            width = 3.dp,
            TdsColor.SHADOW.getColor(),
        ),
    ) {
        content()
    }
}

@Composable
fun TdsCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = TdsColor.BACKGROUND.getColor(),
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 5.dp,
        ),
        border = BorderStroke(
            width = 3.dp,
            TdsColor.SHADOW.getColor(),
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TdsFilledCardPreview() {
    TiTiTheme {
        TdsFilledCard(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.size(100.dp))
        }
    }
}
