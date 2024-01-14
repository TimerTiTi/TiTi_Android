package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsDialog(
    modifier: Modifier = Modifier.background(color = TdsColor.BACKGROUND.getColor()),
    tdsDialogInfo: TdsDialogInfo,
    onShowDialog: (Boolean) -> Unit,
    bodyContent: (@Composable () -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {
            tdsDialogInfo.onDismiss?.invoke()
            onShowDialog(false)
        },
        properties =
        DialogProperties(
            dismissOnBackPress = tdsDialogInfo.cancelable,
            dismissOnClickOutside = tdsDialogInfo.cancelable
        )
    ) {
        Surface(
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(14.dp),
            color = Color.Transparent
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                TdsText(
                    text = tdsDialogInfo.title,
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 17.sp,
                    color = TdsColor.TEXT
                )

                tdsDialogInfo.message?.let { message ->
                    Spacer(modifier = Modifier.height(8.dp))

                    TdsText(
                        text = message,
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 13.sp,
                        color = TdsColor.TEXT
                    )
                }

                if (bodyContent != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    bodyContent()

                    Spacer(modifier = Modifier.height(16.dp))
                }

                TdsDivider()

                when (tdsDialogInfo) {
                    is TdsDialogInfo.Confirm -> {
                        TdsConfirmDialogButtons(
                            tdsDialogInfo = tdsDialogInfo,
                            onShowDialog = onShowDialog
                        )
                    }

                    is TdsDialogInfo.Alert -> {
                        TdsAlertDialogButton(
                            tdsDialogInfo = tdsDialogInfo,
                            onShowDialog = onShowDialog
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TdsConfirmDialogButtons(
    tdsDialogInfo: TdsDialogInfo.Confirm,
    onShowDialog: (Boolean) -> Unit
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TdsTextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                tdsDialogInfo.onNegative?.invoke()
                onShowDialog(false)
            },
            text = tdsDialogInfo.negativeText,
            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
            fontSize = 17.sp,
            textColor = TdsColor.RED
        )

        TdsDivider()

        TdsTextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                tdsDialogInfo.onPositive.invoke()
                onShowDialog(false)
            },
            text = tdsDialogInfo.positiveText,
            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
            fontSize = 16.sp,
            textColor = TdsColor.BLUE
        )
    }
}

@Composable
private fun TdsAlertDialogButton(
    tdsDialogInfo: TdsDialogInfo.Alert,
    onShowDialog: (Boolean) -> Unit
) {
    TdsTextButton(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(44.dp),
        onClick = {
            tdsDialogInfo.onConfirm?.invoke()
            onShowDialog(false)
        },
        text = tdsDialogInfo.confirmText,
        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
        fontSize = 17.sp,
        textColor = TdsColor.BLUE
    )
}

@Preview
@Composable
private fun TdsConfirmDialogPreview() {
    TiTiTheme {
        TdsDialog(
            tdsDialogInfo =
            TdsDialogInfo.Confirm(
                title = "새로운 기록 설정",
                message = "2023.03.10 목표시간 설정",
                cancelable = false,
                onDismiss = {},
                positiveText = "OK",
                onPositive = {},
                negativeText = "Cancel",
                onNegative = {}
            ),
            onShowDialog = {},
            bodyContent = {
                TdsText(
                    text = "tdsDialogInfo.message",
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 12.sp,
                    color = TdsColor.TEXT
                )
            }
        )
    }
}

@Preview
@Composable
private fun TdsAlertDialogPreview() {
    TiTiTheme {
        TdsDialog(
            tdsDialogInfo =
            TdsDialogInfo.Alert(
                title = "새로운 기록 설정",
                message = "2023.03.10 목표시간 설정",
                cancelable = false,
                onDismiss = {},
                confirmText = "Confirm",
                onConfirm = {}
            ),
            onShowDialog = {},
            bodyContent = {
                TdsText(
                    text = "hihi",
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 12.sp,
                    color = TdsColor.TEXT
                )
            }
        )
    }
}
