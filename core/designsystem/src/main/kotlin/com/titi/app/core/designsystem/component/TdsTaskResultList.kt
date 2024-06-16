package com.titi.app.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun TdsTaskResultList(
    modifier: Modifier = Modifier,
    taskData: List<TdsTaskData>,
    colors: List<Color>,
    isSpacing: Boolean,
    leftText: String? = null,
    height: Dp,
    selectedIndex: Int? = null,
    onClickTask: ((taskName: String, index: Int) -> Unit)? = null,
    onClickAddTask: (() -> Unit)? = null,
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
                    isEditMode = selectedIndex == index,
                    onClickTask = if (onClickTask != null) {
                        { onClickTask(it, index) }
                    } else {
                        null
                    },
                )

                if (leftText != null) {
                    TdsDivider(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        color = TdsColor.GRAPH_BORDER,
                    )
                }
            }
        }

        if (selectedIndex == -1) {
            item {
                TdsText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable { onClickAddTask?.invoke() },
                    text = "+ 기록추가",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
