package com.example.playerapp.ui.screen.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton

@Composable
fun SearchScreenTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        ShadowedIconButton(
            drawable = R.drawable.ic_back,
            onClick = { onClickBack.invoke() }
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun SearchScreenTopBarPreview() {
    SearchScreenTopBar(stringResource(id = R.string.search))
}