package com.titi.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun TdsToggleIconButton(
    @DrawableRes checkedIcon: Int,
    @DrawableRes uncheckedIcon: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(if (checked) checkedIcon else uncheckedIcon),
            contentDescription = "toggle_icon_button",
        )
    }
}
