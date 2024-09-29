package com.titi.app.tds.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle

@Composable
fun TtdsText(
    modifier: Modifier = Modifier,
    isNoLocale: Boolean = true,
    text: String,
    textStyle: TtdsTextStyle,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    color: TtdsColor,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text,
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
fun TtdsText(
    modifier: Modifier = Modifier,
    isNoLocale: Boolean = true,
    text: AnnotatedString,
    textStyle: TtdsTextStyle,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    color: TtdsColor,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text,
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