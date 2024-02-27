package com.titi.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun TdsToggleIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes checkedIcon: Int,
    @DrawableRes uncheckedIcon: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Icon(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = true,
                role = Role.Checkbox,
            )
            .then(modifier),
        imageVector = ImageVector.vectorResource(if (checked) checkedIcon else uncheckedIcon),
        contentDescription = "toggle_icon_button",
    )
}
