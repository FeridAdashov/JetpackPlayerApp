package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.utils.DataHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    music: Music,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    val scrollState = rememberScrollState()
    val topBarVisibilityAlpha =
        if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = SheetState(skipPartiallyExpanded = false))
    val bottomSheetCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        mainViewModel.changeControllerVisibility(false)
        mainViewModel.changeBottomBarVisibility(false)
    }

    fun onBackPressed() {
        if (bottomSheetScaffoldState.bottomSheetState.isVisible) {
            bottomSheetCoroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
            return
        }

        mainViewModel.changeControllerVisibility(true)
        mainViewModel.changeBottomBarVisibility(true)
        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                bottomSheetCoroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaylistDetailScreenTopBar(
            stringResource(id = R.string.playing_from_playlist),
            music = music,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 20.dp)
                .alpha(topBarVisibilityAlpha),
            onClickBack = { onBackPressed() },
            onClickMore = { bottomSheetCoroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.partialExpand() } }
        )

        Spacer(modifier = Modifier.height((40 * topBarVisibilityAlpha).dp))
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

    PlaylistDetailScreenMorePanel(
        scaffoldState = bottomSheetScaffoldState,
        scope = bottomSheetCoroutineScope,
        music = music,
        (LocalConfiguration.current.screenHeightDp * 0.5).dp
    )
}


@Preview
@Composable
private fun PlaylistDetailScreenPreview() {
    PlaylistDetailScreen(music = Music("mega hit mix", imageDrawable = R.drawable.img_poster))
}