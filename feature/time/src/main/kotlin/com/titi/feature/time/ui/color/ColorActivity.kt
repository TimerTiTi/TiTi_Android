package com.titi.feature.time.ui.color

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.extension.complementary
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.domain.color.model.TimeColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ColorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TiTiTheme {
                ColorScreen(
                    recordingMode = intent.getIntExtra(RECORDING_MODE_KEY, 1),
                    timeColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(TIME_COLOR_KEY, TimeColor::class.java)
                    } else {
                        intent.getParcelableExtra(TIME_COLOR_KEY)
                    } ?: TimeColor(),
                    onClickCancel = { finish() },
                    onClickConfirm = { finish() }
                )
            }
        }
    }

    companion object {
        const val RECORDING_MODE_KEY = "recordingModeKey"
        const val TIME_COLOR_KEY = "timeColorKey"
    }

}

@Composable
fun ColorScreen(
    viewModel: ColorViewModel = mavericksViewModel(),
    recordingMode: Int,
    timeColor: TimeColor,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit
) {
    val controller = rememberColorPickerController()
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TdsColor.backgroundColor.getColor())
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp
                ),
            controller = controller,
        )

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp
                )
                .height(35.dp),
            controller = controller,
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp
                )
                .height(35.dp),
            controller = controller,
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 24.dp,
                    end = 24.dp
                )
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
                colors = uiState.colors
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ColorButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                ),
            color = controller.selectedColor.value,
            onClickCancel = onClickCancel,
            onClickConfirm = {
                viewModel.addBackgroundColor(
                    colors = uiState.colors,
                    color = controller.selectedColor.value.toArgb().toLong()
                )
                viewModel.updateColor(
                    recordingMode = recordingMode,
                    timeColor = timeColor,
                    color = controller.selectedColor.value.toArgb().toLong()
                )
                onClickConfirm()
            },
        )

    }
}

@Composable
private fun ColorPresetContent(
    modifier: Modifier = Modifier,
    colors: List<Long>
) {
    Column(modifier = modifier) {
        repeat(2) { columnIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(6) { rowIndex ->
                    val index = columnIndex * 6 + rowIndex
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(2.dp, Color.LightGray)
                            .background(Color(colors.getOrNull(index) ?: 0xFFFFFFFF))
                    )
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
            shape = RoundedCornerShape(6.dp),
            onClick = { onClickCancel() }
        ) {
            TdsText(
                text = "취소",
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 16.sp,
                color = TdsColor.textColor
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            shape = RoundedCornerShape(6.dp),
            onClick = { onClickConfirm() }
        ) {
            TdsText(
                text = "설정",
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 16.sp,
                color = color.complementary()
            )
        }
    }
}

@Preview
@Composable
private fun ColorScreenPreview() {
    TiTiTheme {
        ColorScreen(
            recordingMode = 1,
            timeColor = TimeColor(),
            onClickCancel = {},
            onClickConfirm = {}
        )
    }
}