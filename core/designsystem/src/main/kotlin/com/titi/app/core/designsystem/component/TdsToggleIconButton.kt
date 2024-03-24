package com.titi.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor

@Composable
fun TdsToggleIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes checkedIcon: Int,
    @DrawableRes uncheckedIcon: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    tint: TdsColor,
) {
    var isChecked by remember { mutableStateOf(checked) }

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .toggleable(
                value = isChecked,
                onValueChange = {
                    isChecked = !isChecked
                    onCheckedChange.invoke(isChecked)
                },
                enabled = true,
                role = Role.Checkbox,
            )
            .then(modifier),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(if (isChecked) checkedIcon else uncheckedIcon),
            contentDescription = "toggle_icon_button",
            colorFilter = ColorFilter.tint(tint.getColor()),
        )
    }
}
