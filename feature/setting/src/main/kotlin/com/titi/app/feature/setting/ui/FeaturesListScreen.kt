package com.titi.app.feature.setting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    onNavigateWebView: (String) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    Scaffold(
        containerColor = TdsColor.GROUPED_BACKGROUND.getColor(),
        topBar = {
            TopAppBar(
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
                        text = "TiTi 기능들",
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
    onClick: (String) -> Unit,
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
                onClick = { onClick(feature.url) },
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
        FeaturesListScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = FeaturesUiState(features = makeFeatures()),
            onClick = {},
        )
    }
}
