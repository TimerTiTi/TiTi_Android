package com.titi.app.tds.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.titi.app.tds.R
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTheme

@Composable
fun TtdsSmallIcon(
    modifier: Modifier = Modifier,
    tint: TtdsColor = TtdsColor.PRIMARY,
    @DrawableRes icon: Int,
) {
    Icon(
        modifier = Modifier
            .size(22.dp)
            .padding(4.dp)
            .then(modifier),
        painter = painterResource(id = icon),
        tint = tint.getColor(),
        contentDescription = null,
    )
}

@Preview
@Composable
private fun TtdsSmallIconPreview() {
    TtdsTheme {
        TtdsSmallIcon(icon = R.drawable.reset_daily_icon)
    }
}
