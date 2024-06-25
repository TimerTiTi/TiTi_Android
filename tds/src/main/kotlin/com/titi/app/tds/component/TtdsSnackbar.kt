package com.titi.app.tds.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme

@Composable
private fun TtdsSnackbarMessage(
    modifier: Modifier = Modifier,
    emphasizedText: String?,
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
                append("$emphasizedText ")
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
        TtdsSnackbarMessage(
            emphasizedText = "안녕하세요",
            message = "반갑습니다",
        )
    }
}
