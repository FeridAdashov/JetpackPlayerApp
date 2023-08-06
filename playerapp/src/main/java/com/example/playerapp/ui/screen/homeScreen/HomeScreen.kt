package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.domain.entity.MusicListEntity
import com.example.domain.entity.RequestResult
import com.example.playerapp.R
import com.example.playerapp.ui.extensions.navigateToAddMusicScreen
import com.example.playerapp.ui.extensions.navigateToPlaylistDetailsScreen
import com.example.playerapp.ui.globalComponents.ShadowedGradientRoundedContainer
import com.example.playerapp.ui.theme.Cyan
import com.example.playerapp.ui.theme.DarkYellow
import com.example.playerapp.ui.theme.Pink
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.ui.viewModel.MusicViewModel
import com.example.playerapp.ui.viewModel.SimpleMediaViewModel
import com.example.playerapp.utils.DataHelper

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel(),
    musicViewModel: MusicViewModel = hiltViewModel(),
    mediaViewModel: SimpleMediaViewModel,
) {
    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    val musicList = musicViewModel.musicListLiveData.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        if (musicViewModel.musicListLiveData.value == null) musicViewModel.getMusicList()
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        HomeTopBar(
            title = stringResource(id = R.string.good_afternoon),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .alpha(alpha)
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = (30 * alpha).dp),
                text = stringResource(id = R.string.recently_played),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
            Text(
                modifier = Modifier
                    .padding(end = 16.dp, top = (30 * alpha).dp)
                    .clickable {
                        mediaViewModel.setMusicList(DataHelper.recentlyPlayedMusicList)
                        mainViewModel.changeControllerVisibility(true)
                    },
                text = stringResource(id = R.string.play_all),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            items(DataHelper.recentlyPlayedMusicList) {
                RecentlyPlayedView(music = it) {
                    mediaViewModel.setMusic(it)
                    mainViewModel.changeControllerVisibility(true)
                }
            }
        }

        YourMusicList(navController, musicList) {
            navController.navigateToAddMusicScreen()
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 40.dp),
            text = stringResource(id = R.string.try_something_else),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            items(DataHelper.recentlyPlayedMusicList) {
                ExtraSuggestionView(music = it)
            }
        }
    }
}

@Composable
fun YourMusicList(
    navController: NavHostController,
    musicList: State<RequestResult<MusicListEntity>?>,
    addMusic: () -> Unit
) {
    val listStatus = remember { mutableStateOf("") }
    val mList = musicList.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.to_get_you_started),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        if (mList == null)
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        else if (listStatus.value.isNotBlank())
            Text(text = listStatus.value, style = TextStyle(color = White))
    }
    if (mList != null) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            val suggestionListColors = listOf(Pink, DarkYellow, Cyan)

            if (mList is RequestResult.Success && !mList.body.musicList.isNullOrEmpty()) {
                mList.body.musicList!!.forEachIndexed { index, music ->
                    item {
                        SuggestionAlbumView(
                            music = music,
                            titleColor = suggestionListColors[index % suggestionListColors.size]
                        ) {
                            navController.navigateToPlaylistDetailsScreen(music)
                        }
                    }
                }
            } else {
                listStatus.value = "Empty list"
            }
            item {
                AddNewMusicWidget {
                    addMusic.invoke()
                }
            }
        }

    }
}

@Composable
fun AddNewMusicWidget(onClick: () -> Unit) {
    ShadowedGradientRoundedContainer(
        modifier = Modifier
            .width(130.dp)
            .height(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    onClick.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier,
                text = stringResource(R.string.add_new_music),
                style = MaterialTheme.typography.titleSmall,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview
//@Composable
//private fun HomeScreenPreview() {
//    HomeScreen(mainViewModel = MainViewModel(), mediaViewModel = mediaViewModel)
//}
//
