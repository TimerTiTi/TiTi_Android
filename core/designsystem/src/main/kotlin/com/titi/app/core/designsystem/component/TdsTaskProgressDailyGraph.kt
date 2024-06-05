package com.titi.app.core.designsystem.component

import android.graphics.Picture
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.extension.times
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.createCaptureImageModifier

@Composable
fun TdsTaskProgressDailyGraph(
    modifier: Modifier = Modifier,
    todayDate: String,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    checked: Boolean? = null,
    picture: Picture? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val size = maxWidth.coerceAtMost(365.dp)

        OutlinedCard(
            modifier = Modifier
                .createCaptureImageModifier(picture = picture)
                .size(size)
                .padding(10.dp),
            shape = RoundedCornerShape(size * 0.07),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            border = BorderStroke(
                width = 3.dp,
                TdsColor.SHADOW.getColor(),
            ),
        ) {
            Column(
                modifier = Modifier.padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                TdsText(
                    text = todayDate,
                    textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                    fontSize = (size.value * 0.07).sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(15.dp))

                TdsPieChart(
                    modifier = Modifier.weight(1f),
                    taskData = taskData,
                    colors = tdsColors.map { it.getColor() },
                    containsDonut = true,
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        onCheckedChange?.let {
            Box(
                modifier = Modifier
                    .offset(
                        x = -size / 2 + 36.dp,
                        y = -size * 0.49 + 36.dp,
                    ),
            ) {
                TdsToggleIconButton(
                    checkedIcon = R.drawable.checked_icon,
                    uncheckedIcon = R.drawable.unchecked_icon,
                    checked = checked ?: false,
                    onCheckedChange = onCheckedChange,
                    tint = TdsColor.TEXT,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TdsTaskProgressDailyGraphPreview() {
    val taskData = listOf(
        TdsTaskData(
            key = "수업",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "인공지능",
            value = "03:00:00",
            progress = 0.3f,
        ),
        TdsTaskData(
            key = "알고리즘",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "개발",
            value = "03:00:00",
            progress = 0.3f,
        ),
    )

    TiTiTheme {
        TdsTaskProgressDailyGraph(
            modifier = Modifier.fillMaxWidth(),
            todayDate = "2024.02.04",
            taskData = taskData,
            tdsColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
                TdsColor.D3,
                TdsColor.D4,
                TdsColor.D5,
                TdsColor.D6,
                TdsColor.D7,
            ),
            checked = false,
            picture = Picture(),
            onCheckedChange = {},
        )
    }
}
