package com.example.playerapp.ui.globalComponents.playerController

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playerapp.ui.theme.AppBarIconUnselected

@Composable
fun ControllerIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes desc: Int,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .width(20.dp)
            .clickable {
                onClick.invoke()
            },
        painter = painterResource(id = drawable),
        contentDescription = stringResource(id = desc),
        tint = if (isActive) Color.White else AppBarIconUnselected
    )
}