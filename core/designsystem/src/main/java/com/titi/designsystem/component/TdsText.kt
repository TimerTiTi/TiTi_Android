package com.titi.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsText(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle,
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
        style = textStyle,
        textDecoration = textDecoration
    )
}

@Preview
@Composable
private fun TdsTextPreview() {
    TiTiTheme {
        TdsText(
            text = "ABC",
            color = TiTiTheme.colors.blackColor,
            textStyle = TiTiTheme.textStyle.normalTextStyle.copy(fontSize = 24.sp)
        )
    }
}