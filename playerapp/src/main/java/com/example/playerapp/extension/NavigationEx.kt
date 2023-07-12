package com.example.playerapp.extension

import androidx.navigation.NavHostController
import com.example.playerapp.ui.constants.NavigationConstants.SEARCH_SCREEN_CATEGORY_ARG
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicCategoryType
import com.example.playerapp.ui.model.SocialNetwork
import com.example.playerapp.ui.navigation.NavigationScreens

fun NavHostController.navigateToPlaylistDetailsScreen(music: Music) {
    navigate("${NavigationScreens.PlaylistDetails.route}/${music.toJson()}")
}

fun NavHostController.navigateToAboutAlbumScreen(
    album: Album,
    socialNetworks: List<SocialNetwork>? = null,
) {
    var route = "${NavigationScreens.AboutAlbum.route}/${album.toJson()}"

    if (!socialNetworks.isNullOrEmpty())
        route += "/${socialNetworks.toJson()}"
    navigate(route)
}

fun NavHostController.navigateToSearchScreen(categoryType: MusicCategoryType? = null) {
    var route = ""
    categoryType?.let { t ->
        route = "?$SEARCH_SCREEN_CATEGORY_ARG=${t.toJson() ?: ""}"
    }
    navigate("${NavigationScreens.Search.route}$route")
}