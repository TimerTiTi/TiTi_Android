package com.titi.app.feature.edit.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.asMavericksArgs
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDivider
import com.titi.app.core.designsystem.component.TdsGraphContent
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.dialog.AddTaskNameDialog
import com.titi.app.core.designsystem.component.dialog.EditTaskNameDialog
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.util.toOnlyTime
import com.titi.app.feature.edit.mapper.toFeatureModel
import com.titi.app.feature.edit.model.DateTimeTaskHistory
import com.titi.app.feature.edit.model.EditActions
import com.titi.app.feature.edit.model.EditUiState
import java.time.LocalDate

@Composable
fun EditScreen(currentDate: String, onBack: () -> Unit) {
    val viewModel: EditViewModel = mavericksViewModel(
        argsFactory = {
            currentDate.asMavericksArgs()
        },
    )

    val uiState by viewModel.collectAsState()

    EditScreen(
        uiState = uiState,
        onEditActions = {
            when (it) {
                is EditActions.Navigates.Back -> onBack()
                is EditActions.Updates -> viewModel.handleEditActions(it)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditScreen(uiState: EditUiState, onEditActions: (EditActions) -> Unit) {
    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFFFFFFF
    }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(containerColor),
                ),
                title = {
                    TdsText(
                        text = uiState.currentDate.toString().replace('-', '.'),
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )
                },
                navigationIcon = {
                    TdsIconButton(onClick = { onEditActions(EditActions.Navigates.Back) }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = "back",
                            tint = TdsColor.TEXT.getColor(),
                        )
                    }
                },
                actions = {
                    TdsText(
                        modifier = Modifier.clickable { onEditActions(EditActions.Updates.Save) },
                        text = "SAVE",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            with(uiState) {
                TdsGraphContent(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = currentDate.toString().replace('-', '.'),
                    todayDayOfTheWeek = currentDate.dayOfWeek.value - 1,
                    totalTime = dailyGraphData.totalTime,
                    maxTime = dailyGraphData.maxTime,
                    taskData = dailyGraphData.taskData,
                    tdsColors = graphColors,
                    timeLines = dailyGraphData.timeLine,
                    timeTableData = dailyGraphData.tdsTimeTableData,
                    selectedTaskIndex = uiState.selectedTaskIndex,
                    onClickTask = { taskName, index ->
                        onEditActions(
                            EditActions.Updates.ClickTaskName(
                                taskName = taskName,
                                index = index,
                            ),
                        )
                    },
                    onClickAddTask = {
                        onEditActions(
                            EditActions.Updates.ClickTaskName(
                                taskName = "",
                                index = dailyGraphData.taskData.size + 1,
                            ),
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.clickedTaskName != null) {
                EditTaskContent(
                    themeColor = uiState.graphColors.first(),
                    taskName = uiState.clickedTaskName,
                    taskHistories = uiState.currentDaily
                        .taskHistories
                        ?.get(uiState.clickedTaskName)
                        ?.map { it.toFeatureModel() }
                        ?: emptyList(),
                    fullTaskHistories = uiState.currentDaily
                        .taskHistories
                        ?.values
                        ?.flatten()
                        ?.map { it.toFeatureModel() }
                        ?: emptyList(),
                    onEditActions = onEditActions,
                )
            } else {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    TdsText(
                        text = "과목을 선택하여 기록수정 후\nSAVE를 눌러 주세요.",
                        color = TdsColor.TEXT,
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun EditTaskContent(
    themeColor: TdsColor,
    taskName: String,
    taskHistories: List<DateTimeTaskHistory>,
    fullTaskHistories: List<DateTimeTaskHistory>,
    onEditActions: (EditActions) -> Unit,
) {
    var showEditTaskNameDialog by remember {
        mutableStateOf(false)
    }
    var showEditTaskHistoryDialog by remember {
        mutableStateOf(false)
    }
    var selectedTaskHistory by remember {
        mutableStateOf<DateTimeTaskHistory?>(null)
    }

    if (showEditTaskNameDialog) {
        if (taskName.isNotEmpty()) {
            EditTaskNameDialog(
                taskName = TextFieldValue(
                    text = taskName,
                    selection = TextRange(taskName.length),
                ),
                onPositive = {
                    onEditActions(
                        EditActions.Updates.UpdateTaskName(
                            currentTaskName = taskName,
                            updateTaskName = it.text,
                        ),
                    )
                },
                onShowDialog = { showEditTaskNameDialog = it },
            )
        } else {
            AddTaskNameDialog(
                onPositive = {
                    if (it.isNotEmpty()) {
                        onEditActions(
                            EditActions.Updates.UpdateTaskName(
                                currentTaskName = taskName,
                                updateTaskName = it,
                            ),
                        )
                    }
                },
                onShowDialog = { showEditTaskNameDialog = it },
            )
        }
    }

    if (showEditTaskHistoryDialog) {
        EditTaskHistoryTimeDialog(
            themeColor = themeColor,
            startDateTime = selectedTaskHistory?.startDateTime ?: LocalDate.now().atStartOfDay(),
            endDateTime = selectedTaskHistory?.endDateTime ?: LocalDate.now().atStartOfDay(),
            fullTaskHistories = fullTaskHistories
                .toMutableList()
                .apply { remove(selectedTaskHistory) }
                .toList(),
            onShowDialog = { showEditTaskHistoryDialog = it },
            onPositive = { dateTimeTaskHistory ->
                onEditActions(
                    EditActions.Updates.UpsertTaskHistory(
                        taskName = taskName,
                        dateTimeTaskHistory = dateTimeTaskHistory,
                    ),
                )
            },
        )
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        val width = maxWidth.coerceAtMost(345.dp)

        OutlinedCard(
            modifier = Modifier
                .width(width)
                .height(280.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TdsColor.BACKGROUND.getColor()),
            border = BorderStroke(
                width = 3.dp,
                TdsColor.SHADOW.getColor(),
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Task:",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(35.dp))

                    TdsText(
                        modifier = Modifier
                            .background(
                                color = themeColor
                                    .getColor()
                                    .copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(4.dp),
                        text = taskName.ifEmpty { "과목명을 입력해 주세요." },
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                showEditTaskNameDialog = true
                            },
                        painter = painterResource(
                            if (taskName.isBlank()) {
                                R.drawable.plus_circle_icon
                            } else {
                                R.drawable.edit_circle_icon
                            },
                        ),
                        contentDescription = "",
                        tint = TdsColor.TEXT.getColor(),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Time:",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(35.dp))

                    TdsText(
                        text = taskHistories.sumOf { it.diffTime }.getTimeString(),
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 20.sp,
                        color = themeColor,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    TdsText(
                        text = "Histories:",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.TEXT,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(taskHistories.size) { index ->
                            TaskRowContent(
                                themeColor = themeColor,
                                taskHistory = taskHistories[index],
                                onEditTaskHistory = { taskHistory ->
                                    selectedTaskHistory = taskHistory
                                    showEditTaskHistoryDialog = true
                                },
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTaskHistory = null
                                        showEditTaskHistoryDialog = true
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    painter = painterResource(R.drawable.plus_circle_icon),
                                    contentDescription = "",
                                    tint = TdsColor.TEXT.getColor(),
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                TdsText(
                                    text = "기록추가",
                                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                                    color = TdsColor.TEXT,
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }

                TdsText(
                    modifier = Modifier
                        .width(75.dp)
                        .height(25.dp)
                        .background(
                            color = if (taskHistories.isEmpty() || taskName.isEmpty()) {
                                TdsColor.GROUPED_BACKGROUND.getColor()
                            } else {
                                themeColor.getColor()
                            },
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(4.dp)
                        .clickable {
                            if (taskHistories.isNotEmpty() || taskName.isEmpty()) {
                                onEditActions(EditActions.Updates.Done)
                            }
                        },
                    text = "OK",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    textAlign = TextAlign.Center,
                    color = TdsColor.TEXT,
                    fontSize = 17.sp,
                )
            }
        }
    }
}

@Composable
private fun TaskRowContent(
    themeColor: TdsColor,
    taskHistory: DateTimeTaskHistory,
    onEditTaskHistory: (DateTimeTaskHistory) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TdsText(
                modifier = Modifier
                    .width(64.dp)
                    .background(
                        color = themeColor
                            .getColor()
                            .copy(alpha = 0.5f),
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(vertical = 4.dp),
                text = taskHistory.startDateTime.toOnlyTime(),
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 17.sp,
                color = TdsColor.TEXT,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.width(8.dp))

            TdsText(
                text = "~",
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 17.sp,
                color = TdsColor.TEXT,
            )

            Spacer(modifier = Modifier.width(8.dp))

            TdsText(
                modifier = Modifier
                    .width(64.dp)
                    .background(
                        color = themeColor
                            .getColor()
                            .copy(alpha = 0.5f),
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(vertical = 4.dp),
                text = taskHistory.endDateTime.toOnlyTime(),
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 17.sp,
                color = TdsColor.TEXT,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.width(8.dp))

            TdsText(
                text = taskHistory.diffTime.getTimeString(),
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 13.sp,
                color = TdsColor.TEXT,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onEditTaskHistory(taskHistory) },
                painter = painterResource(R.drawable.edit_circle_icon),
                contentDescription = "",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TdsDivider(color = themeColor)
    }
}
