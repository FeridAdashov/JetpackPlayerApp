package com.example.playerapp.ui.screen.aboutAlbumScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.utils.DataHelper

@Composable
fun AboutAlbumScreenTopBar(
    album: Album,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        ShadowedIconButton(drawable = R.drawable.ic_back, onClick = onClickBack)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = album.artistName,
            style = MaterialTheme.typography.titleSmall,
            color = TitleGray,
            textAlign = TextAlign.Center,
            minLines = 1
        )
    }
}


@Preview
@Composable
fun PlaylistDetailScreenTopBarPreview() {
    AboutAlbumScreenTopBar(
        album = DataHelper.album
    )
}