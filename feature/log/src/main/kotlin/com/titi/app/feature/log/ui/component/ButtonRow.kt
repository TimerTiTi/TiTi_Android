package com.titi.app.feature.log.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun ButtonRow(
    modifier: Modifier = Modifier,
    isCreate: Boolean,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit,
    onCreateEditClick: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val width = maxWidth.coerceAtMost(345.dp)

        Row(
            modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.width(100.dp).clickable { onSaveClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
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
                modifier = Modifier.width(100.dp).clickable { onShareClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
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

            Row(
                modifier = Modifier.width(100.dp).clickable { onCreateEditClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = ImageVector.vectorResource(
                        if (isCreate) {
                            R.drawable.log_create_icon
                        } else {
                            R.drawable.log_edit_icon
                        },
                    ),
                    contentDescription = "createEdit",
                    tint = TdsColor.TEXT.getColor(),
                )

                Spacer(modifier = Modifier.width(4.dp))

                TdsText(
                    text = if (isCreate) "Create" else "Edit",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ButtonRowPreview() {
    TiTiTheme {
        ButtonRow(
            modifier = Modifier.fillMaxWidth(),
            isCreate = true,
            onSaveClick = {},
            onShareClick = {},
            onCreateEditClick = {},
        )
    }
}
