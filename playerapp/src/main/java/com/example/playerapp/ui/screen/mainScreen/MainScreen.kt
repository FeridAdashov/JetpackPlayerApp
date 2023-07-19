package com.example.playerapp.ui.screen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.ui.navigation.NavGraph
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.ui.viewModel.SimpleMediaViewModel
import com.example.playerapp.utils.screenBackGradient

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    mediaViewModel: SimpleMediaViewModel
) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .background(brush = screenBackGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (viewModel.bottomBarVisibility)
                    BottomBar(navController = navController, mediaViewModel = mediaViewModel)
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(navController = navController, mediaViewModel = mediaViewModel)
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenScreenPreview() {
//    PlayerAppTheme(dynamicColor = false) {
//        MainScreen(MainViewModel(), simpleMediaViewModel)
//    }
//}