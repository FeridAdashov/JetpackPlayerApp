package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PosterView(modifier: Modifier = Modifier, imageUrl: String) {
    Card(
        modifier = modifier,
        shape = CircleShape.copy(CornerSize(32.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "poster",
        )
    }
}


@Preview
@Composable
private fun PlaylistDetailScreenPosterPreview() {
    PosterView(imageUrl = "https://shorturl.at/pLPT4")
}