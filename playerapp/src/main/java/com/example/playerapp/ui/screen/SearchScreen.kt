package com.example.playerapp.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchScreen(navController: NavHostController = rememberNavController()) {
    Text(text = "")
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

