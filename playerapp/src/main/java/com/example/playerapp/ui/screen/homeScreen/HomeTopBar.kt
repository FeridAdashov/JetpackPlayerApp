package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton

@Composable
fun HomeTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickFlash: () -> Unit = {},
    onClickCategory: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
        )
        ShadowedIconButton(drawable = R.drawable.ic_flash, onClick = onClickFlash)
        ShadowedIconButton(
            modifier = Modifier.padding(start = 26.dp),
            drawable = R.drawable.ic_category,
            onClick = onClickCategory
        )
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(stringResource(id = R.string.good_afternoon))
}