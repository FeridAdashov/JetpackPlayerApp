package com.example.playerapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playerapp.extension.fromJson
import com.example.playerapp.ui.constants.NavigationConstants.ABOUT_ALBUM_SCREEN_ALBUM_ARG
import com.example.playerapp.ui.constants.NavigationConstants.ABOUT_ALBUM_SCREEN_SOCIALS_ARG
import com.example.playerapp.ui.constants.NavigationConstants.PLAYLIST_DETAILS_SCREEN_MUSIC_ARG
import com.example.playerapp.ui.constants.NavigationConstants.SEARCH_SCREEN_CATEGORY_ARG
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicCategoryType
import com.example.playerapp.ui.model.SocialNetwork
import com.example.playerapp.ui.screen.LibraryScreen
import com.example.playerapp.ui.screen.PremiumScreen
import com.example.playerapp.ui.screen.aboutAlbumScreen.AlbumDetailScreen
import com.example.playerapp.ui.screen.homeScreen.HomeScreen
import com.example.playerapp.ui.screen.playlistDetailScreen.PlaylistDetailScreen
import com.example.playerapp.ui.screen.searchScreen.SearchScreen
import com.example.playerapp.ui.screen.searchTabScreen.SearchTabScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationScreens.Home.route) {
        composable(route = NavigationScreens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = NavigationScreens.SearchTabScreen.route) {
            SearchTabScreen(navController = navController)
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
        composable(
            route = "${NavigationScreens.AboutAlbum.route}/{$ABOUT_ALBUM_SCREEN_ALBUM_ARG}/{$ABOUT_ALBUM_SCREEN_SOCIALS_ARG}",
            arguments = listOf(navArgument(ABOUT_ALBUM_SCREEN_ALBUM_ARG) {
                type = NavType.StringType
            }, navArgument(ABOUT_ALBUM_SCREEN_SOCIALS_ARG) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            it.arguments?.getString(ABOUT_ALBUM_SCREEN_ALBUM_ARG)?.let { jsonString ->
                val album = jsonString.fromJson(Album::class.java)
                val socials = Gson().fromJson<ArrayList<SocialNetwork>>(
                    it.arguments?.getString(ABOUT_ALBUM_SCREEN_SOCIALS_ARG), object :
                        TypeToken<ArrayList<SocialNetwork>>() {}.type
                )

                AlbumDetailScreen(
                    album = album,
                    socialNetworks = socials,
                    navController = navController,
                )
            }
        }

        composable(
            route = "${NavigationScreens.Search.route}?$SEARCH_SCREEN_CATEGORY_ARG={$SEARCH_SCREEN_CATEGORY_ARG}",
            arguments = listOf(
                navArgument(SEARCH_SCREEN_CATEGORY_ARG) {
                    type = NavType.StringType
                    nullable = true
                },
            )
        ) {
            val category = it.arguments?.getString(SEARCH_SCREEN_CATEGORY_ARG)
                ?.fromJson(MusicCategoryType::class.java)

            SearchScreen(
                category = category,
                navController = navController,
            )
        }
    }
}