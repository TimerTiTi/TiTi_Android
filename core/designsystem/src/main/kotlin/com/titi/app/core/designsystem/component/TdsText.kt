package com.titi.app.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsText(
    modifier: Modifier = Modifier,
    isNoLocale: Boolean = true,
    text: String? = null,
    textStyle: TdsTextStyle,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    color: TdsColor,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text ?: "",
        modifier = modifier,
        color = color.getColor(),
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = textStyle.getTextStyle(
            isNoLocale = isNoLocale,
            fontSize = fontSize,
        ),
        textDecoration = textDecoration,
    )
}

@Composable
fun TdsText(
    modifier: Modifier = Modifier,
    isNoLocale: Boolean = true,
    text: String? = null,
    textStyle: TdsTextStyle,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    color: Color,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text ?: "",
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = textStyle.getTextStyle(
            isNoLocale = isNoLocale,
            fontSize = fontSize,
        ),
        textDecoration = textDecoration,
    )
}

@Preview
@Composable
private fun TdsTextPreview() {
    TiTiTheme {
        TdsText(
            text = "ABC",
            color = TdsColor.TEXT,
            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
            fontSize = 40.sp,
        )
    }
}
