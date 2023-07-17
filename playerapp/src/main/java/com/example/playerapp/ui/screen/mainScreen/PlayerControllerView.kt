package com.example.playerapp.ui.screen.mainScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.theme.AppBarIconUnselected
import com.example.playerapp.ui.theme.Background
import com.example.playerapp.ui.theme.Blue
import com.example.playerapp.ui.viewModel.MainViewModel


@Composable
fun PlayerControllerView(
    modifier: Modifier = Modifier,
    music: Music,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val appContext = LocalContext.current.applicationContext

    var currentPosition by remember { mutableFloatStateOf(20f) }
    val progress by animateFloatAsState(targetValue = currentPosition)

    Column(modifier = modifier.fillMaxWidth()) {
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
                ControllerIcon(
                    Modifier, R.drawable.ic_pause, R.string.pause, true
                ) {
//                    mainViewModel.playerViewModel.pause(appContext)
                }
                ControllerIcon(Modifier, R.drawable.ic_volume, R.string.volume, false) {}
                ControllerIcon(
                    Modifier,
                    R.drawable.ic_close,
                    R.string.close_player,
                    false
                ) {
//                    mainViewModel.playerViewModel.stop(appContext)
                    mainViewModel.changeControllerVisibility(false)
                }
            }
        }
        Slider(
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
            })
    }
}

@Composable
fun ControllerIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    @StringRes desc: Int,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .width(20.dp)
            .clickable {
                onClick.invoke()
            },
        painter = painterResource(id = drawable),
        contentDescription = stringResource(id = desc),
        tint = if (isActive) Color.White else AppBarIconUnselected
    )
}

@Preview
@Composable
private fun PlayerControllerViewOverview() {
    PlayerControllerView(
        music = Music(title = "Don’t forget your roots - 2021", ""),
        mainViewModel = MainViewModel()
    )
}
