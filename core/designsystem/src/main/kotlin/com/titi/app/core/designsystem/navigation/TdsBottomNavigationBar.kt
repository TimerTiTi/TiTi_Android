package com.titi.app.core.designsystem.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TdsBottomNavigationBar(
    currentTopLevelDestination: TopLevelDestination,
    bottomNavigationColor: Long,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .windowInsetsPadding(
                WindowInsets.systemBarsIgnoringVisibility.only(
                    WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
                ),
            )
            .selectableGroup()
            .background(Color(bottomNavigationColor)),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TopLevelDestination.entries.forEach { destination ->
            val selected = currentTopLevelDestination == destination
            TdsBottomNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.iconResourceId),
                        contentDescription = stringResource(id = destination.titleTextId),
                        tint = if (selected) {
                            TdsColor.TEXT.getColor()
                        } else {
                            TdsColor.LIGHT_GRAY.getColor()
                        },
                    )
                },
                label = {
                    TdsText(
                        text = stringResource(id = destination.titleTextId),
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 16.sp,
                        color = if (selected) {
                            TdsColor.TEXT.getColor()
                        } else {
                            TdsColor.LIGHT_GRAY.getColor()
                        },
                    )
                },
            )
        }
    }
}
