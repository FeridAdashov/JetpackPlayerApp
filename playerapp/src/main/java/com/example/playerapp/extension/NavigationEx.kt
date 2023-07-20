package com.example.playerapp.extension

import androidx.navigation.NavHostController
import com.example.playerapp.ui.constants.NavigationConstants.SEARCH_SCREEN_CATEGORY_ARG
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicCategoryType
import com.example.playerapp.ui.model.SocialNetwork
import com.example.playerapp.ui.navigation.NavigationScreens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavHostController.navigateToPlaylistDetailsScreen(music: Music) {
    val m = music.copy(url = URLEncoder.encode(music.url, StandardCharsets.UTF_8.toString()))
    navigate("${NavigationScreens.PlaylistDetails.route}/${m.toJson()}")
}

fun NavHostController.navigateToAboutAlbumScreen(
    album: Album,
    socialNetworks: List<SocialNetwork>? = null,
) {
    val a = album.copy(
        artistUrl = URLEncoder.encode(
            album.artistUrl ?: "",
            StandardCharsets.UTF_8.toString()
        ),
        posterUrl = URLEncoder.encode(album.posterUrl ?: "", StandardCharsets.UTF_8.toString())
    )

    var route = "${NavigationScreens.AboutAlbum.route}/${a.toJson()}"

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