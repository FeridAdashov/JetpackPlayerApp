package com.example.playerapp.ui.screen.searchTabScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton

@Composable
fun SearchTabScreenTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickHome: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )
        ShadowedIconButton(
            drawable = R.drawable.ic_camera,
            onClick = onClickHome
        )
    }
}

@Preview
@Composable
private fun SearchScreenTopBarPreview() {
    SearchTabScreenTopBar(stringResource(id = R.string.search))
}