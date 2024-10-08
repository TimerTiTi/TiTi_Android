package com.titi.app.feature.time.ui.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsTaskListItem
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTextButton
import com.titi.app.core.designsystem.model.TdsTask
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.DraggableItem
import com.titi.app.core.designsystem.util.dragContainer
import com.titi.app.core.designsystem.util.rememberDragDropState
import com.titi.app.core.util.getTimeToLong
import com.titi.app.domain.task.model.Task
import com.titi.app.feature.time.model.TaskUiState
import com.titi.app.tds.component.TtdsInputTimeTextField
import com.titi.app.tds.component.dialog.AddTaskNameDialog
import com.titi.app.tds.component.dialog.EditTaskNameDialog
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskBottomSheet(
    viewModel: TaskViewModel = mavericksViewModel(),
    themeColor: Color,
    onCloseBottomSheet: () -> Unit,
) {
    val uiState by viewModel.collectAsState()

    var showAddTaskDialog by remember { mutableStateOf(false) }
    var editMode by remember { mutableStateOf(false) }

    if (showAddTaskDialog) {
        AddTaskNameDialog(
            onPositive = { taskName ->
                if (taskName.isNotEmpty()) {
                    viewModel.addTask(taskName)
                }
            },
            onShowDialog = { showAddTaskDialog = it },
        )
    }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 30.dp),
        onDismissRequest = onCloseBottomSheet,
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        tonalElevation = 0.dp,
        containerColor = TdsColor.SECONDARY_BACKGROUND.getColor(),
        contentColor = TdsColor.SECONDARY_BACKGROUND.getColor(),
        dragHandle = null,
    ) {
        TaskBottomSheet(
            uiState = uiState,
            themeColor = themeColor,
            editMode = editMode,
            onClickEditButton = {
                editMode = !editMode
            },
            onClickAddButton = {
                showAddTaskDialog = true
            },
            onClickTask = {
                viewModel.updateRecordTask(it)
                onCloseBottomSheet()
            },
            onClickTargetTimeEditButton = {
                viewModel.updateTask(it)
            },
            onClickTargetTimeSwitch = {
                viewModel.updateTask(it)
            },
            onModifyTaskName = {
                viewModel.updateTaskName(it.first, it.second)
            },
            onDeleteTask = {
                viewModel.updateTask(it)
            },
            onTaskMove = {
                viewModel.moveTask(
                    uiState.tasks[it.first],
                    uiState.tasks[it.second],
                )
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskBottomSheet(
    uiState: TaskUiState,
    themeColor: Color,
    editMode: Boolean,
    onClickEditButton: () -> Unit,
    onClickAddButton: () -> Unit,
    onClickTask: (Task) -> Unit,
    onClickTargetTimeEditButton: (Task) -> Unit,
    onClickTargetTimeSwitch: (Task) -> Unit,
    onModifyTaskName: (Pair<Task, String>) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onTaskMove: (Pair<Int, Int>) -> Unit,
) {
    var hour by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }
    var editTaskName by remember { mutableStateOf(TextFieldValue("")) }

    var showTaskTargetTimeDialog by remember { mutableStateOf(false) }
    var showTaskNameModifyDialog by remember { mutableStateOf(false) }

    var editTask by remember {
        mutableStateOf(
            Task(
                id = 0,
                position = 0,
                taskName = "",
            ),
        )
    }

    if (showTaskTargetTimeDialog) {
        hour = ""
        minutes = ""
        seconds = ""

        TtdsDialog(
            ttdsDialogInfo = TtdsDialogInfo.Confirm(
                title = editTask.taskName,
                message = stringResource(id = R.string.tasks_popup_settasktargettime),
                positiveText = stringResource(id = R.string.common_text_ok),
                onPositive = {
                    val targetTime = getTimeToLong(hour, minutes, seconds)
                    if (targetTime > 0) {
                        onClickTargetTimeEditButton(editTask.copy(taskTargetTime = targetTime))
                    }
                },
                negativeText = stringResource(id = R.string.common_text_cancel),
            ),
            onShowDialog = {
                showTaskTargetTimeDialog = it
            },
        ) {
            TtdsInputTimeTextField(
                modifier = Modifier.padding(horizontal = 15.dp),
                hour = hour,
                onHourChange = { hour = it },
                minutes = minutes,
                onMinutesChange = { minutes = it },
                seconds = seconds,
                onSecondsChange = { seconds = it },
            )
        }
    }

    if (showTaskNameModifyDialog) {
        EditTaskNameDialog(
            taskName = editTaskName,
            onPositive = {
                if (it.text != editTask.taskName) {
                    onModifyTaskName(Pair(editTask, it.text))
                }
            },
            onShowDialog = { showTaskNameModifyDialog = it },
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
            TdsTextButton(
                modifier = Modifier.align(Alignment.CenterStart),
                text = if (editMode) {
                    stringResource(id = R.string.common_text_done)
                } else {
                    stringResource(id = R.string.common_text_edit)
                },
                textColor = themeColor,
                fontSize = 18.sp,
                onClick = onClickEditButton,
            )

            TdsText(
                modifier = Modifier.align(Alignment.Center),
                text = "Tasks",
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 20.sp,
                color = TdsColor.TEXT,
            )

            TdsIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onClickAddButton,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "add",
                    tint = themeColor,
                )
            }
        }

        val listState = rememberLazyListState()
        val dragDropState = rememberDragDropState(lazyListState = listState) { from, to ->
            onTaskMove(Pair(from, to))
        }
        val modifier = if (editMode) {
            Modifier
                .dragContainer(dragDropState)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        } else {
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        }

        LazyColumn(
            modifier = modifier,
            state = listState,
        ) {
            itemsIndexed(uiState.tasks) { index, task ->
                DraggableItem(dragDropState = dragDropState, index = index) { isDragging ->
                    TdsTaskListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDragging) {
                                    TdsColor.DIVIDER.getColor()
                                } else {
                                    TdsColor.SECONDARY_BACKGROUND.getColor()
                                },
                            ),
                        tdsTask = TdsTask(
                            taskTargetTime = task.taskTargetTime,
                            isTaskTargetTimeOn = task.isTaskTargetTimeOn,
                            taskName = task.taskName,
                        ),
                        editMode = editMode,
                        themeColor = themeColor,
                        onClickTask = {
                            if (!editMode) {
                                onClickTask(task)
                            }
                        },
                        onLongClickTask = {
                            editTask = task
                            editTaskName = editTaskName.copy(
                                text = task.taskName,
                                selection = TextRange(task.taskName.length),
                            )
                            showTaskNameModifyDialog = true
                        },
                        onEdit = {
                            editTask = task
                            showTaskTargetTimeDialog = true
                        },
                        onTargetTimeOn = {
                            onClickTargetTimeSwitch(
                                task.copy(
                                    isTaskTargetTimeOn = it,
                                ),
                            )
                        },
                        onDelete = {
                            onDeleteTask(
                                task.copy(
                                    isDelete = true,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskBottomSheetPreview() {
    TiTiTheme {
        TaskBottomSheet(
            uiState =
            TaskUiState(
                tasks = listOf(
                    Task(
                        id = 0,
                        position = 0,
                        taskName = "국어",
                        taskTargetTime = 3600,
                        isTaskTargetTimeOn = false,
                        savedSumTime = 0,
                        isDelete = false,
                    ),
                    Task(
                        id = 1,
                        position = 1,
                        taskName = "영어",
                        taskTargetTime = 3600,
                        isTaskTargetTimeOn = false,
                        savedSumTime = 0,
                        isDelete = false,
                    ),
                    Task(
                        id = 2,
                        position = 2,
                        taskName = "수학",
                        taskTargetTime = 3600,
                        isTaskTargetTimeOn = false,
                        savedSumTime = 0,
                        isDelete = false,
                    ),
                ),
            ),
            themeColor = TdsColor.BLUE.getColor(),
            editMode = true,
            onClickEditButton = { },
            onClickAddButton = { },
            onClickTask = {},
            onClickTargetTimeEditButton = { },
            onClickTargetTimeSwitch = {},
            onModifyTaskName = {},
            onDeleteTask = {},
            onTaskMove = {},
        )
    }
}
