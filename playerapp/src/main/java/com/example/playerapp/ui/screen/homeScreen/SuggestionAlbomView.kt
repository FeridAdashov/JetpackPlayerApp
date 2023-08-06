package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.entity.Music
import com.example.playerapp.R
import com.example.playerapp.ui.theme.Cyan
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.imageComponentGradient


@Composable
fun SuggestionAlbumView(
    modifier: Modifier = Modifier,
    music: Music,
    titleColor: Color,
    onCLick: () -> Unit = {}
) {
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
            )
            .clickable { onCLick.invoke() },
    ) {
        Column(
            modifier = Modifier
                .width(130.dp)
                .heightIn(150.dp)
                .background(
                    brush = imageComponentGradient,
                    shape = CircleShape.copy(CornerSize(26.dp))
                )
                .padding(vertical = 14.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            music.imageUrl?.let { image ->
                AsyncImage(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape.copy(CornerSize(26.dp))),
                    model = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier,
                text = music.title,
                style = MaterialTheme.typography.titleSmall,
                color = titleColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier,
                text = music.title,
                style = TextStyle(fontSize = 10.sp),
                color = Color(0xFF7F8489),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(backgroundColor = 0xFF888080, showBackground = true)
@Composable
fun SuggestionAlbumViewPreview() {
    SuggestionAlbumView(
        music = Music(
            "",
            "daily mix 1", "",
            desc = "six60, mitch james, tiki taane and more",
            imageDrawable = R.drawable.img_music_5
        ),
        titleColor = Cyan
    )
}