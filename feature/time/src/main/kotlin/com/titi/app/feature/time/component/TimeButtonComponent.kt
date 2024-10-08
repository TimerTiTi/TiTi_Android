package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton

@Composable
fun TimeButtonComponent(
    recordingMode: Int,
    tintColor: Color,
    onClickGoalTimeEdit: () -> Unit,
    onClickStartRecord: () -> Unit,
    onClickSettingTimer: (() -> Unit)? = null,
    onClickResetStopwatch: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        TdsIconButton(
            onClick = onClickGoalTimeEdit,
            size = 50.dp,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_record_icon),
                contentDescription = "addRecord",
                tint = tintColor,
            )
        }

        Spacer(modifier = Modifier.width(25.dp))

        TdsIconButton(
            onClick = onClickStartRecord,
            size = 70.dp,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.start_record_icon),
                contentDescription = "startRecord",
                tint = Color.Unspecified,
            )
        }

        Spacer(modifier = Modifier.width(25.dp))

        if (recordingMode == 1) {
            TdsIconButton(
                onClick = { onClickSettingTimer?.invoke() },
                size = 50.dp,
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.setting_timer_time_icon,
                    ),
                    contentDescription = "",
                    tint = tintColor,
                )
            }
        } else {
            TdsIconButton(
                onClick = { onClickResetStopwatch?.invoke() },
                size = 50.dp,
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.setting_stopwatch_time_icon,
                    ),
                    contentDescription = "",
                    tint = tintColor,
                )
            }
        }
    }
}
