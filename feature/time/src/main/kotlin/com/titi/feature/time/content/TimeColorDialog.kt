package com.titi.feature.time.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.core.designsystem.component.TdsDialog
import com.titi.core.designsystem.model.TdsDialogInfo
import com.titi.designsystem.R

@Composable
fun TimeColorDialog(
    backgroundColor: Color,
    textColor: Color,
    recordingMode: Int,
    onNegative: () -> Unit,
    onShowDialog: (Boolean) -> Unit,
    onClickTextColor: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    TdsDialog(
        modifier = Modifier.background(color = Color(0xCCFFFFFF)),
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = stringResource(id = R.string.custom_color),
            positiveText = stringResource(id = R.string.Ok),
            negativeText = stringResource(id = R.string.Cancel),
            onPositive = {},
            onNegative = onNegative,
        ),
        onShowDialog = onShowDialog
    ) {
        ColorSelectContent(
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClickBackgroundColor = {
//                context.startActivity(
//                    Intent(
//                        context,
//                        ColorActivity::class.java
//                    ).apply {
//                        putExtra(ColorActivity.RECORDING_MODE_KEY, recordingMode)
//                    }
//                )
            },
            onClickTextColor = onClickTextColor
        )
        Spacer(Modifier.height(16.dp))
    }
}