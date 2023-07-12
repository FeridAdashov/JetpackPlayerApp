package com.example.playerapp.ui.navigation

import com.example.playerapp.R

sealed class NavigationScreens(val route: String, val title: String, val iconDrawable: Int = 0) {
    object Home : NavigationScreens(
        route = "home",
        title = "Home",
        iconDrawable = R.drawable.ic_home
    )

    object SearchTabScreen : NavigationScreens(
        route = "search_tab_screen",
        title = "Search",
        iconDrawable = R.drawable.ic_search
    )

    object Library : NavigationScreens(
        route = "library",
        title = "Library",
        iconDrawable = R.drawable.ic_library
    )

    object Premium : NavigationScreens(
        route = "premium",
        title = "Premium",
        iconDrawable = R.drawable.ic_premium
    )

    object PlaylistDetails : NavigationScreens(
        route = "playlistDetails",
        title = "PlaylistDetails",
    )

    object AboutAlbum : NavigationScreens(
        route = "aboutAlbum",
        title = "AboutAlbum",
    )

    object Search : NavigationScreens(
        route = "search",
        title = "Search",
    )
}