package com.titi.app.feature.edit.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.util.toOnlyTime
import com.titi.app.feature.edit.mapper.toFeatureModel
import com.titi.app.feature.edit.model.DateTimeTaskHistory
import com.titi.app.feature.edit.model.EditActions
import com.titi.app.feature.edit.model.EditUiState
import com.titi.app.feature.edit.util.isTaskHistoryOverlap
import com.titi.app.tds.component.dialog.AddTaskNameDialog
import com.titi.app.tds.component.dialog.EditTaskNameDialog
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun EditScreen(currentDate: String, onBack: () -> Unit) {
    val viewModel: EditViewModel = mavericksViewModel(
        argsFactory = {
            currentDate.asMavericksArgs()
        },
    )

    val uiState by viewModel.collectAsState()

    var showBackDialog by remember {
        mutableStateOf(false)
    }

    if (showBackDialog) {
        TtdsDialog(
            ttdsDialogInfo = TtdsDialogInfo.Confirm(
                title = stringResource(R.string.edit_popup_nosavetitle),
                positiveText = stringResource(id = R.string.common_text_ok),
                negativeText = stringResource(id = R.string.common_text_cancel),
                onPositive = {
                    onBack()
                },
            ),
            onShowDialog = { showBackDialog = it },
        )
    }

    LaunchedEffect(uiState.finishEvent) {
        if (uiState.finishEvent) {
            onBack()
        }
    }

    EditScreen(
        uiState = uiState,
        onEditActions = {
            when (it) {
                is EditActions.Navigates.Back -> {
                    if (uiState.saveEnabled) {
                        showBackDialog = true
                    } else {
                        onBack()
                    }
                }

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
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    BackHandler {
        onEditActions(EditActions.Navigates.Back)
    }

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
                        modifier = Modifier.clickable {
                            if (uiState.saveEnabled) {
                                onEditActions(EditActions.Updates.Save)
                            }
                        },
                        text = "SAVE",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = if (uiState.saveEnabled) TdsColor.BLUE else TdsColor.LIGHT_GRAY,
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackState)
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
                    currentDate = uiState.currentDate,
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
                    onShowSnackbar = {
                        snackScope.launch {
                            snackState.showSnackbar(it)
                        }
                    },
                )
            } else {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    TdsText(
                        isNoLocale = false,
                        text = stringResource(R.string.editdaily_text_infohowtoeditdaily),
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
    currentDate: LocalDate,
    themeColor: TdsColor,
    taskName: String,
    taskHistories: List<DateTimeTaskHistory>,
    fullTaskHistories: List<DateTimeTaskHistory>,
    onEditActions: (EditActions) -> Unit,
    onShowSnackbar: (String) -> Unit,
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

    val message = stringResource(R.string.edit_text_duplicatehistory)
    if (showEditTaskHistoryDialog) {
        EditTaskHistoryTimeDialog(
            themeColor = themeColor,
            startDateTime = selectedTaskHistory?.startDateTime ?: currentDate.atStartOfDay(),
            endDateTime = selectedTaskHistory?.endDateTime ?: currentDate.atStartOfDay(),
            onShowDialog = { showEditTaskHistoryDialog = it },
            onPositive = { dateTimeTaskHistory ->
                if (
                    !isTaskHistoryOverlap(
                        startDateTime = dateTimeTaskHistory.startDateTime,
                        endDateTime = dateTimeTaskHistory.endDateTime,
                        taskHistories = fullTaskHistories
                            .toMutableList()
                            .apply { remove(selectedTaskHistory) }
                            .toList(),
                    )
                ) {
                    onEditActions(
                        EditActions.Updates.UpsertTaskHistory(
                            taskName = taskName,
                            currentTaskHistory = selectedTaskHistory,
                            updateTaskHistory = dateTimeTaskHistory,
                        ),
                    )
                } else {
                    onShowSnackbar(message)
                }
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
                        isNoLocale = false,
                        text = taskName.ifEmpty { stringResource(R.string.edit_text_notaskname) },
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
                                    .padding(bottom = 30.dp)
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
                                    isNoLocale = false,
                                    text = stringResource(
                                        R.string.editdaily_button_appendnewhistory,
                                    ),
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
                        .wrapContentHeight()
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
                    isNoLocale = false,
                    text = stringResource(R.string.common_text_ok),
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
                painter = painterResource(
                    R.drawable.edit_circle_icon,
                ),
                contentDescription = "",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TdsDivider(color = themeColor)
    }
}
