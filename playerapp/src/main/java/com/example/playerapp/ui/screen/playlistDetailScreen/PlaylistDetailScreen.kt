package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.utils.DataHelper

@Composable
fun PlaylistDetailScreen(
    music: Music,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        mainViewModel.changeControllerVisibility(false)
        mainViewModel.changeBottomBarVisibility(false)
    }

    fun onBackPressed() {
        mainViewModel.changeControllerVisibility(true)
        mainViewModel.changeBottomBarVisibility(true)
        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaylistDetailScreenTopBar(
            stringResource(id = R.string.playing_from_playlist),
            music = music,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 20.dp)
                .alpha(alpha),
            onClickBack = ::onBackPressed
        )

        Spacer(modifier = Modifier.height((40 * alpha).dp))
        music.imageDrawable?.let { image ->
            PosterView(
                modifier = Modifier.size(180.dp, 180.dp),
                drawable = image
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        PlaylistDetailScreenMusicController(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        PlaylistDetailScreenLyrics(
            modifier = Modifier.padding(
                top = 30.dp,
                start = 16.dp,
                end = 16.dp
            ), music = music
        )
        PlaylistDetailScreenEvents(
            modifier = Modifier.padding(
                top = 30.dp,
                start = 16.dp,
                end = 16.dp
            ),
            events = DataHelper.events,
            navController = navController,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}


@Preview
@Composable
fun PlaylistDetailScreenPreview() {
    PlaylistDetailScreen(music = Music("mega hit mix", imageDrawable = R.drawable.img_poster))
}