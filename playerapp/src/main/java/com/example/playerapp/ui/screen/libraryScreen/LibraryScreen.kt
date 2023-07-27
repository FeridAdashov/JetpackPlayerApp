package com.example.playerapp.ui.screen.libraryScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun LibraryScreen(navController: NavHostController = rememberNavController()) {
    Text(text = "")
}

@Preview
@Composable
private fun LibraryScreenPreview() {
    LibraryScreen()
}

