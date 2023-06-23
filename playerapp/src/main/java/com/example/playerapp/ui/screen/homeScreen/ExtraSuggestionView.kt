package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.theme.Cyan
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.imageComponentGradient

@Composable
fun ExtraSuggestionView(modifier: Modifier = Modifier, music: Music) {
    Box(
        modifier = modifier
            .background(
                brush = imageComponentGradient,
                shape = CircleShape.copy(CornerSize(26.dp))
            )
            .shadow(
                ambientColor = White,
                elevation = 20.dp,
                shape = CircleShape.copy(CornerSize(26.dp))
            ),
    ) {
        Column(
            modifier = Modifier
                .width(130.dp)
                .background(
                    brush = imageComponentGradient,
                    shape = CircleShape.copy(CornerSize(26.dp))
                )
                .padding(bottom = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            music.imageDrawable?.let { image ->
                Image(
                    modifier = Modifier.height(130.dp),
                    painter = painterResource(id = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = music.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFF7F8489),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(backgroundColor = 0xFF888080, showBackground = true)
@Composable
fun ExtraSuggestionViewPreview() {
    ExtraSuggestionView(
        music = Music(
            "adam french, bella mt., twiceyoung and",
            imageDrawable = R.drawable.img_music_5
        ),
    )
}