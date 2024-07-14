package com.titi.app.feature.log.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsOutlinedInputTextField
import com.titi.app.core.designsystem.component.TdsTabRow
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.GraphColorUiState
import com.titi.app.feature.log.model.GraphGoalTimeUiState
import com.titi.app.feature.log.ui.LogViewModel
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBottomSheet(
    viewModel: LogViewModel,
    graphColorUiState: GraphColorUiState,
    graphGoalTimeUiState: GraphGoalTimeUiState,
    onDismissRequest: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val keyboard = LocalSoftwareKeyboardController.current

    var showMonthGoalTimeDialog by remember {
        mutableStateOf(false)
    }
    var showWeekGoalTimeDialog by remember {
        mutableStateOf(false)
    }
    var goalTime by remember {
        mutableStateOf("")
    }

    if (showMonthGoalTimeDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(id = R.string.common_text_targettime),
                message = stringResource(id = R.string.common_popup_setmonthtargettime),
                cancelable = false,
                positiveText = stringResource(id = R.string.common_text_ok),
                onPositive = {
                    viewModel.updateGraphGoalTime(
                        monthGoalTime = goalTime.toIntOrNull(),
                        graphGoalTimeUiState = graphGoalTimeUiState,
                    )
                },
                negativeText = stringResource(id = R.string.common_text_cancel),
            ),
            onShowDialog = {
                showMonthGoalTimeDialog = it
                goalTime = ""
            },
        ) {
            val monthGoalTimeFocusRequester = remember { FocusRequester() }

            LaunchedEffect(monthGoalTimeFocusRequester) {
                monthGoalTimeFocusRequester.requestFocus()
                awaitFrame()
                keyboard?.show()
            }

            TdsOutlinedInputTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .padding(horizontal = 15.dp)
                    .focusRequester(monthGoalTimeFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        showMonthGoalTimeDialog = false
                        viewModel.updateGraphGoalTime(
                            monthGoalTime = goalTime.toIntOrNull(),
                            graphGoalTimeUiState = graphGoalTimeUiState,
                        )
                    },
                ),
                fontSize = 17.sp,
                text = goalTime,
                placeHolder = {
                    TdsText(
                        text = "100",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.DIVIDER,
                    )
                },
                onValueChange = {
                    goalTime = it
                },
            )
        }
    }

    if (showWeekGoalTimeDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(id = R.string.common_text_targettime),
                message = stringResource(id = R.string.common_popup_setweektargettime),
                cancelable = false,
                positiveText = stringResource(id = R.string.common_text_ok),
                onPositive = {
                    viewModel.updateGraphGoalTime(
                        weekGoalTime = goalTime.toIntOrNull(),
                        graphGoalTimeUiState = graphGoalTimeUiState,
                    )
                },
                negativeText = stringResource(id = R.string.common_text_cancel),
            ),
            onShowDialog = {
                showWeekGoalTimeDialog = it
                goalTime = ""
            },
        ) {
            val weekGoalTimeFocusRequester = remember { FocusRequester() }

            LaunchedEffect(weekGoalTimeFocusRequester) {
                weekGoalTimeFocusRequester.requestFocus()
                awaitFrame()
                keyboard?.show()
            }

            TdsOutlinedInputTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .padding(horizontal = 15.dp)
                    .focusRequester(weekGoalTimeFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        showWeekGoalTimeDialog = false
                        viewModel.updateGraphGoalTime(
                            weekGoalTime = goalTime.toIntOrNull(),
                            graphGoalTimeUiState = graphGoalTimeUiState,
                        )
                    },
                ),
                fontSize = 17.sp,
                text = goalTime,
                placeHolder = {
                    TdsText(
                        text = "30",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 17.sp,
                        color = TdsColor.DIVIDER,
                    )
                },
                onValueChange = {
                    goalTime = it
                },
            )
        }
    }

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
        ),
        tonalElevation = 0.dp,
        containerColor = TdsColor.BACKGROUND.getColor(),
        contentColor = TdsColor.BACKGROUND.getColor(),
        dragHandle = null,
    ) {
        SettingBottomSheetContent(
            colorDirection = when (graphColorUiState.direction) {
                GraphColor.GraphDirection.Right -> 0
                GraphColor.GraphDirection.Left -> 1
            },
            graphColors = graphColorUiState.graphColors,
            monthGoalTime = "${graphGoalTimeUiState.monthGoalTime} H",
            weekGoalTime = "${graphGoalTimeUiState.weekGoalTime} H",
            onClickColor = {
                viewModel.updateGraphColors(
                    selectedIndex = it,
                    graphColorUiState = graphColorUiState,
                )
            },
            onClickColorDirection = {
                viewModel.updateGraphColors(
                    selectedIndex = graphColorUiState.selectedIndex,
                    graphColorUiState = graphColorUiState,
                )
            },
            onClickMonthGoalTime = {
                showMonthGoalTimeDialog = true
            },
            onClickWeekGoalTime = {
                showWeekGoalTimeDialog = true
            },
        )
    }
}

@Composable
private fun SettingBottomSheetContent(
    colorDirection: Int,
    graphColors: List<TdsColor>,
    monthGoalTime: String,
    weekGoalTime: String,
    onClickColor: (Int) -> Unit,
    onClickColorDirection: (Int) -> Unit,
    onClickMonthGoalTime: () -> Unit,
    onClickWeekGoalTime: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 20.dp),
    ) {
        val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.colorselector_text_color),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.colorselector_text_setgraphcolor),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )
            }

            TdsColorRow(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickColor,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.colorselector_text_colordirectiontitle),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.colorselector_text_colordirectiondesc),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TdsTabRow(
                        modifier = Modifier
                            .width(60.dp)
                            .height(30.dp),
                        selectedItemIndex = colorDirection,
                        items = listOf(
                            "→",
                            "←",
                        ),
                        onClick = onClickColorDirection,
                        indicatorColor = graphColors.first().getColor(),
                    )

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        graphColors
                                            .firstOrNull()
                                            ?.getColor()
                                            ?: TdsColor.D1.getColor(),
                                        graphColors
                                            .getOrNull(2)
                                            ?.getColor()
                                            ?: TdsColor.D2.getColor(),
                                    ),
                                ),
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.common_text_targettime),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.logsetting_text_dailytargettimedesc),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = TdsColor.SEGMENT_BACKGROUND.getColor())
                        .padding(12.dp)
                        .clickable {
                            onClickMonthGoalTime()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Month",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 16.sp,
                        color = TdsColor.TEXT,
                    )

                    TdsText(
                        text = monthGoalTime,
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 14.sp,
                        color = TdsColor.TEXT,
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = TdsColor.SEGMENT_BACKGROUND.getColor())
                        .padding(12.dp)
                        .clickable {
                            onClickWeekGoalTime()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Week",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 16.sp,
                        color = TdsColor.TEXT,
                    )

                    TdsText(
                        text = weekGoalTime,
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 14.sp,
                        color = TdsColor.TEXT,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingBottomSheetContentPreview() {
    TiTiTheme {
        SettingBottomSheetContent(
            colorDirection = 0,
            graphColors = listOf(
                TdsColor.D1,
                TdsColor.D2,
            ),
            monthGoalTime = "100 H",
            weekGoalTime = "30 H",
            onClickColor = {},
            onClickColorDirection = {},
            onClickMonthGoalTime = {},
            onClickWeekGoalTime = {},
        )
    }
}
