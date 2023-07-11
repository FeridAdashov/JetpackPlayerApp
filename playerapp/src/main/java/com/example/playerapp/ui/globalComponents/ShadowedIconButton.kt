package com.example.playerapp.ui.globalComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.IconGradient

@Composable
fun ShadowedIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    tint: Color = White,
    size: Dp = 40.dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                spotColor = White, elevation = 8.dp, shape = CircleShape.copy(CornerSize(20.dp))
            )
            .clickable { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = IconGradient, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = drawable),
                contentDescription = null,
                tint = tint
            )
        }
    }
}

@Preview
@Composable
private fun ShadowedIconButtonPreview() {
    ShadowedIconButton(drawable = R.drawable.ic_flash)
}