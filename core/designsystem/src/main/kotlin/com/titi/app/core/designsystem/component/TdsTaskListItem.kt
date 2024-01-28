package com.titi.app.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsTask
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TdsTaskListItem(
    modifier: Modifier = Modifier,
    tdsTask: TdsTask,
    editMode: Boolean,
    themeColor: Color,
    onClickTask: () -> Unit,
    onLongClickTask: () -> Unit,
    onEdit: () -> Unit,
    onTargetTimeOn: (Boolean) -> Unit,
    onDelete: () -> Unit,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .combinedClickable(
                        onClick = onClickTask,
                        onLongClick = onLongClickTask,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(visible = editMode) {
                    TdsIconButton(
                        modifier = Modifier.padding(end = 12.dp),
                        onClick = { onDelete() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel_icon),
                            contentDescription = "cancel",
                            tint = TdsColor.RED.getColor(),
                        )
                    }
                }

                Column {
                    TdsText(
                        text = tdsTask.taskName,
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 20.sp,
                        color = TdsColor.TEXT,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    AnimatedVisibility(visible = tdsTask.isTaskTargetTimeOn) {
                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            TdsText(
                                text =
                                stringResource(
                                    R.string.task_set_goal_time,
                                    tdsTask.taskTargetTime.getTimeString(),
                                ),
                                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                                fontSize = 14.sp,
                                color = TdsColor.LIGHT_GRAY,
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            TdsText(
                                modifier = Modifier.clickable { onEdit() },
                                text = stringResource(R.string.edit),
                                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                                fontSize = 14.sp,
                                color = themeColor,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = tdsTask.isTaskTargetTimeOn,
                    onCheckedChange = onTargetTimeOn,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = themeColor,
                        checkedBorderColor = Color.Transparent,
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = TdsColor.DIVIDER.getColor(),
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = Color.White,
                    ),
                )
            }

            AnimatedVisibility(visible = editMode) {
                Icon(
                    modifier = Modifier.padding(start = 12.dp),
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "menu",
                    tint = TdsColor.LIGHT_GRAY.getColor(),
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
                taskName = "English",
            ),
            editMode = true,
            themeColor = TdsColor.BLUE.getColor(),
            onClickTask = {},
            onLongClickTask = {},
            onEdit = {},
            onTargetTimeOn = {},
            onDelete = {},
        )
    }
}
