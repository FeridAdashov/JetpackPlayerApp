package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.extension.navigateToPlaylistDetailsScreen
import com.example.playerapp.ui.theme.Cyan
import com.example.playerapp.ui.theme.DarkYellow
import com.example.playerapp.ui.theme.Pink
import com.example.playerapp.utils.DataHelper

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

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

        Text(
            modifier = Modifier.padding(start = 16.dp, top = (30 * alpha).dp),
            text = stringResource(id = R.string.recently_played),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            items(DataHelper.recentlyPlayedMusicList) {
                RecentlyPlayedView(music = it)
            }
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 40.dp),
            text = stringResource(id = R.string.to_get_you_started),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 30.dp)
        ) {
            val suggestionListColors = listOf(Pink, DarkYellow, Cyan)

            DataHelper.recentlyPlayedMusicList.forEachIndexed { index, music ->
                item {
                    SuggestionAlbumView(
                        music = music,
                        titleColor = suggestionListColors[index % suggestionListColors.size]
                    ) {
                        navController.navigateToPlaylistDetailsScreen(music)
                    }
                }
            }
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

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}

