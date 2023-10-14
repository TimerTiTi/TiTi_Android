package com.titi.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.designsystem.theme.TdsColor
import com.titi.designsystem.theme.TdsTextStyle
import com.titi.designsystem.theme.TiTiTheme

@Composable
fun TdsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TdsTextStyle = TdsTextStyle.normalTextStyle,
    textColor: TdsColor,
    fontSize: TextUnit,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .widthIn(min = 36.dp)
            .heightIn(min = 36.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(12.dp, 8.dp)
    ) {
        TdsText(
            text = text,
            textStyle = textStyle,
            color = textColor,
            fontSize = fontSize
        )
    }

}

@Preview
@Composable
private fun TdsButtonPreview() {
    TiTiTheme {
        Column {
            TdsTextButton(
                text = "ABC",
                fontSize = 16.sp,
                onClick = { },
                textColor = TdsColor.textColor
            )
        }
    }
}