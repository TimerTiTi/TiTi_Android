package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor

@Composable
fun TdsTaskResultList(
    modifier: Modifier = Modifier,
    taskData: List<TdsTaskData>,
    colors: List<Color>,
    isSpacing: Boolean,
    leftText: String? = null,
    height: Dp,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = if (leftText != null) Arrangement.Top else Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(taskData) { index, pie ->
            Column {
                TdsTaskResultListItem(
                    height = height,
                    taskName = pie.key,
                    taskTotalTime = pie.value,
                    color = colors[index % colors.size],
                    isSpacing = isSpacing,
                    leftText = when (leftText) {
                        null -> null
                        "Top" -> "Top${index + 1}"
                        else -> leftText
                    },
                )

                if (leftText != null) {
                    TdsDivider(color = TdsColor.GRAPH_BORDER)
                }
            }
        }
    }
}
