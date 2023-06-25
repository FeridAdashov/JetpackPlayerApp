package com.example.playerapp.ui.screen.aboutAlbumScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.ui.globalComponents.ShadowedGradientRoundedContainer
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.model.SocialNetwork
import com.example.playerapp.ui.screen.playlistDetailScreen.PosterView
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.utils.BlueButtonGradient
import com.example.playerapp.utils.DataHelper
import com.example.playerapp.utils.screenBackGradient


@Composable
fun AlbumDetailScreen(
    album: Album,
    socialNetworks: List<SocialNetwork>? = null,
    navController: NavHostController = rememberNavController()
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        mainViewModel.changeControllerVisibility(true)
        mainViewModel.changeBottomBarVisibility(true)
    }

    fun onBackPressed() {
        mainViewModel.changeControllerVisibility(false)
        mainViewModel.changeBottomBarVisibility(false)
        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AboutAlbumScreenTopBar(
            album = album,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 20.dp)
                .alpha(alpha),
            onClickBack = ::onBackPressed
        )

        Spacer(modifier = Modifier.height((40 * alpha).dp))

        ShadowedGradientRoundedContainer(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
        ) {
            album.posterDrawable?.let { image ->
                PosterView(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.4f), drawable = image
                )
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "%,d".format(album.monthlyListenersCount).replace(",", ", "),
                style = TextStyle(color = White, fontSize = 28.sp, fontWeight = FontWeight.W600)
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Monthly Listeners",
                style = MaterialTheme.typography.titleSmall,
                color = TitleGray,
            )

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .shadow(elevation = 8.dp, spotColor = Color.Black)
                    .background(brush = BlueButtonGradient, shape = RoundedCornerShape(20.dp))
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "${album.worldPlace}st in the world",
                    style = TextStyle(
                        color = White, fontSize = 14.sp, fontWeight = FontWeight.W600
                    )
                )
            }

            album.bio?.let { bio ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                        .background(brush = screenBackGradient, shape = RoundedCornerShape(26.dp))
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                        text = bio,
                        style = MaterialTheme.typography.titleSmall,
                        color = TitleGray,
                    )
                }
            }

            album.artistDrawable?.let { drawable ->
                Card(
                    modifier = Modifier.padding(top = 40.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, color = White)
                ) {
                    Image(
                        modifier = Modifier.size(72.dp),
                        painter = painterResource(id = drawable),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Post by",
                    style = MaterialTheme.typography.titleSmall,
                    color = TitleGray,
                )
                Text(
                    text = album.artistName,
                    style = TextStyle(color = White, fontSize = 18.sp, fontWeight = FontWeight.W600)
                )

                if (!socialNetworks.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        socialNetworks.forEach {
                            SocialNetworkView(socialNetwork = it)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SocialNetworkView(modifier: Modifier = Modifier, socialNetwork: SocialNetwork) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(0.3.dp, color = White),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(25.dp),
            painter = painterResource(id = socialNetwork.iconDrawable!!),
            contentDescription = socialNetwork.name,
            tint = White
        )
    }
}

@Preview
@Composable
private fun SocialNetworkPreview() {
    SocialNetworkView(socialNetwork = DataHelper.socialNetworks[0])
}

@Preview
@Composable
private fun PlaylistDetailScreenPreview() {
    AlbumDetailScreen(album = DataHelper.album, socialNetworks = DataHelper.socialNetworks)
}