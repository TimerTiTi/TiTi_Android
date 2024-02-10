package com.titi.app.feature.color.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.extension.complementary
import com.titi.app.core.designsystem.extension.hexCode
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.color.model.ColorUiState

@Composable
fun ColorScreen(
    viewModel: ColorViewModel = mavericksViewModel(),
    recordingMode: Int,
    onFinish: () -> Unit,
) {
    val uiState by viewModel.collectAsState()
    val controller = rememberColorPickerController()

    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableLongStateOf(0L) }
    if (showDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = stringResource(R.string.setting_background_text),
                cancelable = false,
                positiveText = stringResource(id = R.string.Ok),
                onPositive = {
                    viewModel.updateColor(
                        recordingMode = recordingMode,
                        color = selectedColor,
                    )
                    onFinish()
                },
                negativeText = stringResource(id = R.string.Cancel),
            ),
            onShowDialog = { showDialog = it },
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(selectedColor))
                    .border(2.dp, Color.LightGray),
            )
        }
    }

    ColorScreen(
        uiState = uiState,
        controller = controller,
        onShowDialog = {
            selectedColor = it
            showDialog = true
        },
        onClickCancel = onFinish,
        onClickConfirm = {
            viewModel.addBackgroundColor(
                colors = uiState.colors,
                color = controller.selectedColor.value.toArgb().toLong(),
            )
            viewModel.updateColor(
                recordingMode = recordingMode,
                color = controller.selectedColor.value.toArgb().toLong(),
            )
            onFinish()
        },
    )
}

@Composable
private fun ColorScreen(
    uiState: ColorUiState,
    controller: ColorPickerController,
    onShowDialog: (Long) -> Unit,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        HsvColorPicker(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp,
                ),
            controller = controller,
        )

        TdsText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = "#${controller.selectedColor.value.hexCode}",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 18.sp,
            color = TdsColor.WHITE,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(10.dp))

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp,
                )
                .height(35.dp),
            controller = controller,
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp,
                )
                .height(35.dp),
            controller = controller,
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
        ) {
            AlphaTile(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(2.dp, Color.LightGray),
                controller = controller,
            )

            Spacer(modifier = Modifier.width(10.dp))

            ColorPresetContent(
                modifier = Modifier.fillMaxWidth(),
                colors = uiState.colors,
                onShowDialog = onShowDialog,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ColorButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                ),
            color = controller.selectedColor.value,
            onClickCancel = onClickCancel,
            onClickConfirm = onClickConfirm,
        )
    }
}

@Composable
private fun ColorPresetContent(
    modifier: Modifier = Modifier,
    colors: List<Long>,
    onShowDialog: (Long) -> Unit,
) {
    Column(modifier = modifier) {
        repeat(2) { columnIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(6) { rowIndex ->
                    val index = columnIndex * 6 + rowIndex
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(2.dp, Color.LightGray)
                            .background(Color.White),
                    ) {
                        colors.getOrNull(index)?.let {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(it))
                                    .clickable { onShowDialog(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorButtons(
    modifier: Modifier = Modifier,
    color: Color,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    Row(modifier = modifier) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(6.dp),
            onClick = { onClickCancel() },
        ) {
            TdsText(
                text = stringResource(id = R.string.Cancel),
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                fontSize = 16.sp,
                color = TdsColor.RED,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            shape = RoundedCornerShape(6.dp),
            onClick = { onClickConfirm() },
        ) {
            TdsText(
                text = stringResource(id = R.string.Ok),
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                fontSize = 16.sp,
                color = color.complementary(),
            )
        }
    }
}

@Preview(widthDp = 600, heightDp = 600)
@Composable
private fun ColorScreenPreview() {
    TiTiTheme {
        ColorScreen(
            uiState = ColorUiState(),
            controller = rememberColorPickerController(),
            onClickConfirm = {},
            onClickCancel = {},
            onShowDialog = {},
        )
    }
}
