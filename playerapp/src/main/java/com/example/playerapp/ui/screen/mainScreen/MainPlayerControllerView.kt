package com.example.playerapp.ui.screen.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.playerController.ControllerIcon
import com.example.playerapp.ui.globalComponents.playerController.PlayerBar
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.theme.playerSliderColors
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.ui.viewModel.SimpleMediaViewModel
import com.example.playerapp.ui.viewModel.UIEvent
import com.example.playerapp.ui.viewModel.UIState
import com.rcudev.player_service.service.MediaPlayerService


@Composable
fun MainPlayerControllerView(
    modifier: Modifier = Modifier,
    music: Music,
    mainViewModel: MainViewModel = hiltViewModel(),
    mediaViewModel: SimpleMediaViewModel,
) {
    val state = mediaViewModel.uiState.collectAsStateWithLifecycle()
    val appContext = LocalContext.current.applicationContext

    val (progress, progressString) = Pair(mediaViewModel.progress, mediaViewModel.progressString)

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        when (state.value) {
            UIState.Buffering -> CircularProgressIndicator()
            is UIState.Error -> {
                Text(
                    text = (state.value as UIState.Error).message,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Yellow,
                    overflow = TextOverflow.Ellipsis
                )
            }

            UIState.Ready -> {
                MediaPlayerService.start(appContext)

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_six60),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = music.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (mediaViewModel.isPlaying)
                            ControllerIcon(
                                Modifier, R.drawable.ic_pause, R.string.pause, true
                            ) {
                                mediaViewModel.onUIEvent(UIEvent.Pause)
                            } else {
                            ControllerIcon(
                                Modifier, R.drawable.ic_play, R.string.play, true
                            ) {
                                mediaViewModel.onUIEvent(UIEvent.Play)
                            }
                        }
                        ControllerIcon(Modifier, R.drawable.ic_volume, R.string.volume, false) {}
                        ControllerIcon(
                            Modifier,
                            R.drawable.ic_close,
                            R.string.close_player,
                            false
                        ) {
                            mediaViewModel.onUIEvent(UIEvent.Stop)
                            MediaPlayerService.stop(appContext)
                            mainViewModel.changeControllerVisibility(false)
                        }
                    }
                }
                PlayerBar(
                    progress = progress,
                    durationString = mediaViewModel.formatDuration(mediaViewModel.duration),
                    progressString = progressString,
                    onUiEvent = mediaViewModel::onUIEvent,
                    colors = playerSliderColors(),
                )
            }

            UIState.Initial -> {}
        }
    }
}

//@Preview
//@Composable
//private fun PlayerControllerViewOverview() {
//    PlayerControllerView(
//        music = Music(title = "Don’t forget your roots - 2021", ""),
//        mainViewModel = MainViewModel()
//    )
//}
