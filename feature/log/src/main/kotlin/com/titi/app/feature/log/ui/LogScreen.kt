package com.titi.app.feature.log.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.component.TdsTabRow
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogScreen() {
    val scope = rememberCoroutineScope()
    val verticalScroll = rememberScrollState()
    var tabSelectedIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState(
        pageCount = {
            3
        },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 25.dp,
                vertical = 15.dp,
            )
            .verticalScroll(verticalScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TdsTabRow(
            modifier = Modifier
                .width(150.dp)
                .height(30.dp),
            selectedItemIndex = tabSelectedIndex,
            items = listOf("Home", "Daily", "Week"),
            onClick = {
                tabSelectedIndex = it
                scope.launch {
                    pagerState.scrollToPage(it)
                }
            },
        )

        Spacer(modifier = Modifier.height(15.dp))

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            TdsText(
                text = "$page",
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                fontSize = 40.sp,
                color = TdsColor.SEGMENT_INDICATIOR,
            )
        }
    }
}

@Preview
@Composable
private fun LogScreenPreview() {
    TiTiTheme {
        LogScreen()
    }
}
