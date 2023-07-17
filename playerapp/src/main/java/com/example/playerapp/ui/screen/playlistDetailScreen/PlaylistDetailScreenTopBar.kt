package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White

@Composable
fun PlaylistDetailScreenTopBar(
    title: String,
    music: Music,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
    onClickMore: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShadowedIconButton(drawable = R.drawable.ic_back, onClick = onClickBack)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = TitleGray,
                textAlign = TextAlign.Center,
                minLines = 1
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = music.title,
                style = MaterialTheme.typography.titleSmall,
                color = White,
                textAlign = TextAlign.Center,
                minLines = 1
            )
        }
        ShadowedIconButton(
            drawable = R.drawable.ic_more,
            onClick = onClickMore
        )
    }
}


@Preview
@Composable
private fun PlaylistDetailScreenTopBarPreview() {
    PlaylistDetailScreenTopBar(
        stringResource(id = R.string.playing_from_playlist),
        music = Music("Mega hit mix","",)
    )
}