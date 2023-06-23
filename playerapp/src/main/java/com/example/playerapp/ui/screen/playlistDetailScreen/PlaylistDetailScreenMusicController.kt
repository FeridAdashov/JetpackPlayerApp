package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedIconButton
import com.example.playerapp.ui.screen.mainScreen.ControllerIcon
import com.example.playerapp.ui.theme.Background
import com.example.playerapp.ui.theme.Blue
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White

@Composable
fun PlaylistDetailScreenMusicController(modifier: Modifier = Modifier) {
    var currentPosition by remember { mutableFloatStateOf(20f) }
    val progress by animateFloatAsState(targetValue = currentPosition)

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
        Slider(
            modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            value = progress,
            colors = SliderDefaults.colors(
                activeTrackColor = Blue,
                activeTickColor = Blue,
                thumbColor = Blue,
                inactiveTrackColor = Background
            ),
            valueRange = 0f..100f,
            onValueChange = { sliderValue_ ->
                currentPosition = sliderValue_
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShadowedIconButton(drawable = R.drawable.ic_suffle, tint = White, size = 38.dp)
            ShadowedIconButton(drawable = R.drawable.ic_previous, tint = White, size = 48.dp)
            ShadowedIconButton(drawable = R.drawable.ic_pause, tint = White, size = 58.dp)
            ShadowedIconButton(drawable = R.drawable.ic_next, tint = White, size = 48.dp)
            ShadowedIconButton(drawable = R.drawable.ic_repeat, tint = White, size = 38.dp)
        }
    }
}


@Preview
@Composable
fun PlaylistDetailScreenMusicControllerPreview() {
    PlaylistDetailScreenMusicController()
}
