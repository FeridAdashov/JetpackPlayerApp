package com.example.playerapp.ui.screen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.ui.navigation.NavGraph
import com.example.playerapp.ui.theme.PlayerAppTheme
import com.example.playerapp.utils.screenBackGradient
import com.example.playerapp.ui.viewModel.MainViewModel


@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .background(brush = screenBackGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (viewModel.bottomBarVisibility)
                    BottomBar(navController = navController)
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenScreenPreview() {
    PlayerAppTheme(dynamicColor = false) {
        MainScreen()
    }
}