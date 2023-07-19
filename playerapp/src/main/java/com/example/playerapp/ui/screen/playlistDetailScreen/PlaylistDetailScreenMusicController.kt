package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton
import com.example.playerapp.ui.globalComponents.playerController.ControllerIcon
import com.example.playerapp.ui.globalComponents.playerController.PlayerBar
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.theme.playerSliderColors
import com.example.playerapp.ui.viewModel.SimpleMediaViewModel
import com.example.playerapp.ui.viewModel.UIEvent

@Composable
fun PlaylistDetailScreenMusicController(
    modifier: Modifier = Modifier,
    mediaViewModel: SimpleMediaViewModel,
) {
    val (progress, progressString) = Pair(mediaViewModel.progress, mediaViewModel.progressString)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Miss you",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "Oliver Tree, Robin Schulz",
                    style = MaterialTheme.typography.titleSmall,
                    color = TitleGray,
                    textAlign = TextAlign.Center,
                    minLines = 1
                )
            }
            ControllerIcon(Modifier, R.drawable.ic_heart, R.string.love, true) {}
        }
        PlayerBar(
            progress = progress,
            durationString = mediaViewModel.formatDuration(mediaViewModel.duration),
            progressString = progressString,
            onUiEvent = mediaViewModel::onUIEvent,
            colors = playerSliderColors(),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShadowedIconButton(drawable = R.drawable.ic_suffle, tint = White, size = 38.dp)

            ShadowedIconButton(drawable = R.drawable.ic_previous, tint = White, size = 48.dp) {
                mediaViewModel.onUIEvent(UIEvent.Previous)
            }

            if (mediaViewModel.isPlaying)
                ShadowedIconButton(drawable = R.drawable.ic_pause, tint = White, size = 58.dp) {
                    mediaViewModel.onUIEvent(UIEvent.Pause)
                }
            else ShadowedIconButton(drawable = R.drawable.ic_play, tint = White, size = 58.dp) {
                mediaViewModel.onUIEvent(UIEvent.Play)
            }

            ShadowedIconButton(drawable = R.drawable.ic_next, tint = White, size = 48.dp) {
                mediaViewModel.onUIEvent(UIEvent.Next)
            }

            ShadowedIconButton(drawable = R.drawable.ic_repeat, tint = White, size = 38.dp)
        }
    }
}


//@Preview
//@Composable
//private fun PlaylistDetailScreenMusicControllerPreview() {
//    PlaylistDetailScreenMusicController()
//}
