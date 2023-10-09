package com.titi.designsystem.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.titi.designsystem.model.TdsDialogInfo
import com.titi.designsystem.theme.TdsColor
import com.titi.designsystem.theme.TdsTextStyle
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsDialog(
    tdsDialogInfo: TdsDialogInfo,
    onShowDialog: (Boolean) -> Unit,
    bodyContent: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            tdsDialogInfo.onDismiss?.invoke()
            onShowDialog(false)
        },
        properties = DialogProperties(
            dismissOnBackPress = tdsDialogInfo.cancelable,
            dismissOnClickOutside = tdsDialogInfo.cancelable
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.background(color = TdsColor.backgroundColor.getColor()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                TdsText(
                    text = tdsDialogInfo.title,
                    textStyle = TdsTextStyle.extraBoldTextStyle,
                    fontSize = 20.sp,
                    color = TdsColor.labelColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = tdsDialogInfo.message,
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 16.sp,
                    color = TdsColor.labelColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                bodyContent()

                Spacer(modifier = Modifier.height(12.dp))

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
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        TdsTextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                tdsDialogInfo.onNegative?.invoke()
                onShowDialog(false)
            },
            text = tdsDialogInfo.negativeText,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 16.sp,
            textColor = TdsColor.wrongTextFieldColor,
        )

        TdsDivider()

        TdsTextButton(
            modifier = Modifier.weight(1f),
            onClick = {
                tdsDialogInfo.onPositive.invoke()
                onShowDialog(false)
            },
            text = tdsDialogInfo.positiveText,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 16.sp,
            textColor = TdsColor.tintColor,
        )
    }
}

@Composable
private fun TdsAlertDialogButton(
    tdsDialogInfo: TdsDialogInfo.Alert,
    onShowDialog: (Boolean) -> Unit
) {
    TdsTextButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        onClick = {
            tdsDialogInfo.onConfirm?.invoke()
            onShowDialog(false)
        },
        text = tdsDialogInfo.confirmText,
        textStyle = TdsTextStyle.normalTextStyle,
        fontSize = 16.sp,
        textColor = TdsColor.tintColor,
    )
}

@Preview
@Composable
private fun TdsConfirmDialogPreview() {
    TiTiTheme {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
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
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 12.sp,
                    color = TdsColor.labelColor
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
            tdsDialogInfo = TdsDialogInfo.Alert(
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
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 12.sp,
                    color = TdsColor.labelColor
                )
            }
        )
    }
}