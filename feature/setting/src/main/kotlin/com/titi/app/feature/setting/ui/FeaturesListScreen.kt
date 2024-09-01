package com.titi.app.feature.setting.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.setting.model.FeaturesUiState
import com.titi.app.feature.setting.model.makeFeatures

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesListScreen(
    viewModel: FeaturesListViewModel = mavericksViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateWebView: (title: String, url: String) -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.collectAsState()

    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFF2F2F7
    }

    LaunchedEffect(Unit) {
        viewModel.updateFeatures(context.makeFeatures())
    }

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TdsColor.GROUPED_BACKGROUND.getColor(),
                ),
                navigationIcon = {
                    TdsIconButton(onClick = onNavigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = "back",
                            tint = TdsColor.TEXT.getColor(),
                        )
                    }
                },
                title = {
                    TdsText(
                        isNoLocale = false,
                        text = stringResource(R.string.settings_button_functions),
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.TEXT,
                    )
                },
            )
        },
    ) {
        FeaturesListScreen(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(it),
            uiState = uiState,
            onClick = onNavigateWebView,
        )
    }
}

@Composable
fun FeaturesListScreen(
    modifier: Modifier = Modifier,
    uiState: FeaturesUiState,
    onClick: (title: String, url: String) -> Unit,
) {
    Column(modifier = modifier) {
        uiState.features.forEachIndexed { index, feature ->
            ListContent(
                title = feature.title,
                rightAreaContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right_icon),
                        contentDescription = "",
                        tint = TdsColor.LIGHT_GRAY.getColor(),
                    )
                },
                onClick = { onClick(feature.title, feature.url) },
            )

            if (index != uiState.features.size - 1) {
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}

@Composable
@Preview
private fun FeaturesListScreenPreview() {
    TiTiTheme {
        val context = LocalContext.current
        FeaturesListScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = FeaturesUiState(features = context.makeFeatures()),
            onClick = { _, _ -> },
        )
    }
}
