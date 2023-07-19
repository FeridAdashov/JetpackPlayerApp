package com.example.playerapp.ui.screen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.navigation.NavigationScreens
import com.example.playerapp.ui.theme.AppBarIconSelected
import com.example.playerapp.ui.theme.AppBarIconUnselected
import com.example.playerapp.ui.theme.Background
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.ui.viewModel.SimpleMediaViewModel
import com.example.playerapp.utils.appBarGradient


@Composable
fun BottomBar(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    mediaViewModel: SimpleMediaViewModel,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .background(
                brush = appBarGradient,
                shape = RoundedCornerShape(34.dp, 34.dp, 0.dp, 0.dp)
            )
            .padding(horizontal = 10.dp, vertical = 16.dp)
    ) {
        Column {
            if (mainViewModel.playerControllerVisibility)
                MainPlayerControllerView(
                    music = Music("don’t forget your roots - 2021", ""),
                    mediaViewModel = mediaViewModel
                )
            MenuBar(
                modifier = Modifier.padding(top = 10.dp, start = 6.dp, end = 6.dp),
                navController = navController,
                currentDestination = currentDestination,
            )
        }
    }
}

@Composable
fun MenuBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentDestination: NavDestination?,
    radius: Dp = 34.dp,
) {
    val viewModel: MainViewModel = viewModel()

    val screens = listOf(
        NavigationScreens.Home,
        NavigationScreens.SearchTabScreen,
        NavigationScreens.Library,
        NavigationScreens.Premium,
    )

    Surface(
        modifier = modifier
            .height(70.dp)
            .padding(horizontal = 6.dp),
        shape = CircleShape.copy(CornerSize(radius)),
        shadowElevation = 10.dp,
        tonalElevation = 10.dp,
        color = Background,
    ) {
        Surface(
            modifier = Modifier.padding(bottom = 4.dp),
            shape = CircleShape.copy(CornerSize(radius)),
            shadowElevation = 5.dp,
            tonalElevation = 5.dp,
            color = Color.Transparent,
        ) {
            NavigationBar(containerColor = MaterialTheme.colorScheme.tertiary) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    ) {
                        when (screen) {
                            NavigationScreens.Home -> viewModel.changeControllerVisibility(true)
                            NavigationScreens.Library -> viewModel.changeControllerVisibility(true)
                            NavigationScreens.Premium -> viewModel.changeControllerVisibility(false)
                            NavigationScreens.SearchTabScreen -> viewModel.changeControllerVisibility(
                                true
                            )

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
    onClick: () -> Unit
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        modifier = Modifier.padding(top = 4.dp),
        label = { Text(text = if (selected) screen.title else "") },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = screen.iconDrawable),
                contentDescription = "Navigation icon"
            )
        },
        selected = selected,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppBarIconSelected,
            unselectedIconColor = AppBarIconUnselected,
            selectedTextColor = White,
            indicatorColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = {
            if (!selected) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
                onClick.invoke()
            }
        }
    )
}