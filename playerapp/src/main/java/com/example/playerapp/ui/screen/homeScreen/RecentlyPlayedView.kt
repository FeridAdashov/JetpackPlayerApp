package com.example.playerapp.ui.screen.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playerapp.R
import com.example.playerapp.ui.globalComponents.ShadowedGradientRoundedContainer
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.theme.TitleGray

@Composable
fun RecentlyPlayedView(modifier: Modifier = Modifier, music: Music) {
    ShadowedGradientRoundedContainer(
        modifier = modifier.width(110.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        music.imageDrawable?.let { image ->
            Image(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape.copy(CornerSize(26.dp))),
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier,
            text = music.title,
            style = MaterialTheme.typography.titleSmall,
            color = TitleGray,
            textAlign = TextAlign.Center,
            minLines = 2
        )
    }
}

@Preview
@Composable
fun RecentlyPlayedViewPreview() {
    RecentlyPlayedView(music = Music("mega hit mix", imageDrawable = R.drawable.img_music_5))
}