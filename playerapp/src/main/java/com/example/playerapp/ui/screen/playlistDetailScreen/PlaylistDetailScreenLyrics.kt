package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
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
import com.example.playerapp.ui.theme.SecondaryLight
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.imageComponentBorderGradient
import com.example.playerapp.utils.imageComponentGradient

@Composable
fun PlaylistDetailScreenLyrics(modifier: Modifier = Modifier, music: Music) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = imageComponentBorderGradient,
                shape = CircleShape.copy(CornerSize(26.dp))
            )
            .padding(0.5.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = imageComponentGradient,
                    shape = CircleShape.copy(CornerSize(26.dp))
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShadowedIconButton(
                    modifier = Modifier.padding(start = 20.dp),
                    drawable = R.drawable.ic_share
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.lyrics),
                    style = MaterialTheme.typography.titleSmall,
                    color = White,
                    textAlign = TextAlign.Center,
                )
                ShadowedIconButton(
                    modifier = Modifier.padding(end = 20.dp),
                    drawable = R.drawable.ic_maximize
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .background(
                        shape = CircleShape.copy(CornerSize(26.dp)),
                        color = SecondaryLight
                    )
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = " don’t remind me\n" +
                            "i’m minding my own damn business\n" +
                            "don’t try to find me\n" +
                            "do you really think that i could care",
                    style = MaterialTheme.typography.headlineMedium,
                    color = White,
                )
            }
        }
    }
}


@Preview
@Composable
private fun PlaylistDetailScreenLyricsPreview() {
    PlaylistDetailScreenLyrics(music = Music("mega hit mix","",))
}