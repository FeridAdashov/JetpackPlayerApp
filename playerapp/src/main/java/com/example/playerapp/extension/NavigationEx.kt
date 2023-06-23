package com.example.playerapp.extension

import androidx.navigation.NavHostController
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.navigation.NavigationScreens

fun NavHostController.navigateToPlaylistDetailsScreen(music: Music) {
    navigate("${NavigationScreens.PlaylistDetails.route}/${music.toJson()}")
}