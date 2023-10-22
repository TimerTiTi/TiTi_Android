package com.titi.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.core.designsystem.extension.getTimeString
import com.titi.core.designsystem.model.TdsTask
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.designsystem.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TdsTaskListItem(
    modifier: Modifier = Modifier,
    tdsTask: TdsTask,
    editMode: Boolean,
    themeColor: TdsColor,
    onClickTask: () -> Unit,
    onLongClickTask: () -> Unit,
    onEdit: () -> Unit,
    onTargetTimeOn: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onLongClickMenu: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TdsColor.backgroundColor.getColor())
            .combinedClickable(
                onClick = onClickTask,
                onLongClick = onLongClickTask
            )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = editMode) {
                Icon(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { onDelete() },
                    painter = painterResource(id = R.drawable.cancel_icon),
                    contentDescription = "cancel",
                    tint = TdsColor.redColor.getColor()
                )
            }

            Column {
                TdsText(
                    text = tdsTask.taskName,
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 20.sp,
                    color = TdsColor.textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                AnimatedVisibility(visible = tdsTask.isTaskTargetTimeOn) {
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        TdsText(
                            text = stringResource(
                                R.string.task_set_goal_time,
                                tdsTask.taskTargetTime.getTimeString()
                            ),
                            textStyle = TdsTextStyle.normalTextStyle,
                            fontSize = 14.sp,
                            color = TdsColor.lightGrayColor
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        TdsText(
                            modifier = Modifier.clickable { onEdit() },
                            text = stringResource(R.string.edit),
                            textStyle = TdsTextStyle.normalTextStyle,
                            fontSize = 14.sp,
                            color = themeColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = tdsTask.isTaskTargetTimeOn,
                onCheckedChange = onTargetTimeOn,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = themeColor.getColor(),
                    checkedBorderColor = Color.Transparent,
                    checkedThumbColor = Color.White,
                    uncheckedTrackColor = TdsColor.dividerColor.getColor(),
                    uncheckedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                )
            )

            AnimatedVisibility(visible = editMode) {
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { onLongClickMenu() }
                        ),
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "menu",
                    tint = TdsColor.lightGrayColor.getColor()
                )
            }
        }

        TdsDivider()
    }
}

@Preview
@Composable
private fun TdsTaskListItemPreview() {
    TiTiTheme {
        TdsTaskListItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            tdsTask = TdsTask(
                taskTargetTime = 12346,
                isTaskTargetTimeOn = false,
                taskName = "English"
            ),
            editMode = false,
            themeColor = TdsColor.blueColor,
            onClickTask = {},
            onLongClickTask = {},
            onEdit = {},
            onTargetTimeOn = {},
            onDelete = {},
            onLongClickMenu = {},
        )
    }
}