package com.titi.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.designsystem.theme.TdsColor
import com.titi.designsystem.theme.TdsTextStyle
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsText(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TdsTextStyle,
    fontSize : TextUnit,
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
        style = textStyle.getTextStyle(fontSize),
        textDecoration = textDecoration
    )
}

@Preview
@Composable
private fun TdsTextPreview() {
    TiTiTheme {
        TdsText(
            text = "ABC",
            color = TdsColor.blackColor,
            textStyle = TdsTextStyle.blackTextStyle,
            fontSize = 40.sp
        )
    }
}