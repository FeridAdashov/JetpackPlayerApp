package com.example.playerapp.ui.screen.playlistDetailScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.ui.extensions.navigateToAboutAlbumScreen
import com.example.playerapp.ui.globalComponents.ShadowedGradientRoundedContainer
import com.example.playerapp.ui.model.MusicEvent
import com.example.playerapp.ui.model.MusicEventType
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.DataHelper
import com.example.playerapp.utils.IconGradient

@Composable
fun PlaylistDetailScreenEvents(
    events: List<MusicEvent>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(40.dp)) {
        events.map { MusicEventView(event = it, navController) }
    }
}

@Composable
fun MusicEventView(
    event: MusicEvent, navController: NavHostController, modifier: Modifier = Modifier,
) {
    ShadowedGradientRoundedContainer(modifier = modifier.clickable {
        if (event.type == MusicEventType.ABOUT)
            navController.navigateToAboutAlbumScreen(
                album = DataHelper.album,
                socialNetworks = DataHelper.socialNetworks
            )
    }) {
        Text(
            modifier = Modifier,
            text = stringResource(id = if (event.type == MusicEventType.ABOUT) R.string.about_artist else R.string.live_events),
            style = MaterialTheme.typography.titleLarge,
            color = White,
        )
        event.imageDrawable?.let { drawable ->
            Image(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(CircleShape.copy(CornerSize(26.dp))),
                painter = painterResource(id = drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    modifier = Modifier,
                    text = event.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                event.desc?.let { desc ->
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = desc,
                        style = MaterialTheme.typography.titleSmall,
                        color = TitleGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                event.text?.let { text ->
                    var firstLineEndIndex by remember { mutableIntStateOf(0) }

                    Text(
                        modifier = Modifier
                            .animateContentSize()
                            .padding(top = 16.dp),
                        text = text,
                        style = MaterialTheme.typography.titleSmall,
                        color = TitleGray,
                        maxLines = 1,
                        onTextLayout = { textLayoutResult ->
                            if (textLayoutResult.hasVisualOverflow) {
                                firstLineEndIndex = textLayoutResult.getLineEnd(
                                    lineIndex = 0,
                                    visibleEnd = true
                                )
                            }
                        }
                    )
                    if (firstLineEndIndex > 0) {
                        var hasOverflow by remember { mutableStateOf(false) }
                        var expanded by remember { mutableStateOf(false) }

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .weight(1f),
                                text = text.substring(firstLineEndIndex).trim(),
                                style = MaterialTheme.typography.titleSmall,
                                color = TitleGray,
                                maxLines = if (expanded) 4 else 1,
                                overflow = TextOverflow.Ellipsis,
                                onTextLayout = { textLayoutResult ->
                                    hasOverflow = textLayoutResult.hasVisualOverflow
                                }
                            )
                            if (hasOverflow || expanded)
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            expanded = !expanded
                                        },
                                    text = stringResource(if (expanded) R.string.see_less else R.string.see_more),
                                    style = TextStyle(color = White),
                                    fontSize = 12.sp
                                )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .shadow(
                        spotColor = White,
                        elevation = 4.dp,
                        shape = CircleShape.copy(CornerSize(20.dp))
                    )
                    .background(brush = IconGradient, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                    text = stringResource(if (event.type == MusicEventType.ABOUT) R.string.follow else R.string.find_tickets),
                    style = TextStyle(color = White),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlaylistDetailScreenEventsPreview() {
    PlaylistDetailScreenEvents(
        events = DataHelper.events,
        navController = rememberNavController()
    )
}