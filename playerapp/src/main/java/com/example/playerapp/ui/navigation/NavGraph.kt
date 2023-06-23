package com.example.playerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playerapp.extension.fromJson
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.screen.LibraryScreen
import com.example.playerapp.ui.screen.PremiumScreen
import com.example.playerapp.ui.screen.SearchScreen
import com.example.playerapp.ui.screen.homeScreen.HomeScreen
import com.example.playerapp.ui.screen.playlistDetailScreen.PLAYLIST_DETAILS_SCREEN_MUSIC_ARG
import com.example.playerapp.ui.screen.playlistDetailScreen.PlaylistDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationScreens.Home.route) {
        composable(route = NavigationScreens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = NavigationScreens.Search.route) {
            SearchScreen(navController)
        }
        composable(route = NavigationScreens.Library.route) {
            LibraryScreen(navController)
        }
        composable(route = NavigationScreens.Premium.route) {
            PremiumScreen(navController)
        }
        composable(
            route = "${NavigationScreens.PlaylistDetails.route}/{$PLAYLIST_DETAILS_SCREEN_MUSIC_ARG}",
            arguments = listOf(navArgument(PLAYLIST_DETAILS_SCREEN_MUSIC_ARG) {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString(PLAYLIST_DETAILS_SCREEN_MUSIC_ARG)?.let { jsonString ->
                val music = jsonString.fromJson(Music::class.java)
                PlaylistDetailScreen(
                    navController = navController,
                    music = music
                )
            }
        }
    }
}