package com.titi.app.tds.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle

@Composable
fun TtdsText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TtdsTextStyle,
    fontSize: TextUnit,
    color: TtdsColor,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color.getColor(),
        style = textStyle.getTextStyle(),
        fontSize = fontSize,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines,
        textDecoration = textDecoration,
    )
}

@Composable
fun TtdsText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textStyle: TtdsTextStyle,
    fontSize: TextUnit,
    color: TtdsColor,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color.getColor(),
        style = textStyle.getTextStyle(),
        fontSize = fontSize,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        minLines = minLines,
        textDecoration = textDecoration,
    )
}
