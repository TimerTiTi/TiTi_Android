package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.titi.app.core.designsystem.model.TdsPieData

@Composable
fun TdsTaskResultList(
    modifier: Modifier = Modifier,
    pieData: List<TdsPieData>,
    isSpacing: Boolean,
    isCheck: Boolean,
    height: Dp,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(pieData) {
            TdsTaskResultListItem(
                height = height,
                taskName = it.key,
                taskTotalTime = it.value,
                color = it.color,
                isSpacing = isSpacing,
                isCheck = isCheck,
            )
        }
    }
}
