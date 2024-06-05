package com.titi.app.feature.edit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(currentDate: LocalDate, onBack: () -> Unit) {
    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFFFFFFF
    }

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(containerColor),
                ),
                title = {
                    TdsText(
                        text = currentDate.toString(),
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.TEXT,
                    )
                },
                navigationIcon = {
                    TdsIconButton(onClick = onBack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = "back",
                            tint = TdsColor.TEXT.getColor(),
                        )
                    }
                },
                actions = {
                    TdsText(
                        text = "SAVE",
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.TEXT,
                    )
                },
            )
        },
    ) {
        EditScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
private fun EditScreen(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
    }
}

@Preview
@Composable
private fun EditScreenPreview() {
    TiTiTheme {
        EditScreen(currentDate = LocalDate.now()) { }
    }
}
