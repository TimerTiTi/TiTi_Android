package com.titi.app.tds.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.tds.R
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme

@Composable
fun TtdsSnackbar(snackbarData: TtdsSnackbarData) {
    TtdsSnackbar(
        startIcon = snackbarData.visuals.startIcon,
        emphasizedMessage = snackbarData.visuals.emphasizedMessage,
        message = snackbarData.visuals.message,
    )
}

@Composable
fun TtdsSnackbar(
    startIcon: (@Composable () -> Unit)? = null,
    emphasizedMessage: String?,
    message: String,
) {
    TtdsTheme {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color.Black.copy(alpha = 0.12f),
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 14.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (startIcon != null) {
                startIcon()
            }

            TtdsSnackbarMessage(
                emphasizedMessage = emphasizedMessage,
                message = message,
            )
        }
    }
}

@Composable
private fun TtdsSnackbarMessage(
    modifier: Modifier = Modifier,
    emphasizedMessage: String?,
    message: String,
) {
    TtdsText(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = TtdsColor.PRIMARY.getColor(),
                    fontWeight = FontWeight.SemiBold,
                ),
            ) {
                append("$emphasizedMessage ")
            }
            append(message)
        },
        textStyle = TtdsTextStyle.MEDIUM_TEXT_STYLE,
        color = TtdsColor.TEXT,
        fontSize = 14.sp,
    )
}

@Preview
@Composable
private fun TtdsSnackbarMessagePreview() {
    TtdsTheme {
        TtdsSnackbar(
            startIcon = {
                TtdsSmallIcon(icon = R.drawable.reset_daily_icon)
            },
            emphasizedMessage = "안녕하세요",
            message = "반갑습니다",
        )
    }
}
