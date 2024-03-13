package com.titi.app.feature.log.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun ButtonRow(modifier: Modifier = Modifier, onSaveClick: () -> Unit, onShareClick: () -> Unit) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        Row(
            modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.clickable { onSaveClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.save_photo_icon),
                    contentDescription = "save",
                    tint = TdsColor.TEXT.getColor(),
                )

                Spacer(modifier = Modifier.width(4.dp))

                TdsText(
                    text = "Save",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )
            }

            Row(
                modifier = Modifier.clickable { onShareClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.share_icon),
                    contentDescription = "save",
                    tint = TdsColor.TEXT.getColor(),
                )

                Spacer(modifier = Modifier.width(4.dp))

                TdsText(
                    text = "Share",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )
            }
        }
    }
}
