package com.example.playerapp.ui.screen.searchTabScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.extension.navigateToSearchScreen
import com.example.playerapp.ui.model.MusicCategory
import com.example.playerapp.ui.navigation.NavigationScreens
import com.example.playerapp.ui.theme.PrimaryLight
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.DataHelper
import com.example.playerapp.utils.IconGradient

@Composable
fun SearchTabScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SearchTabScreenTopBar(
            title = stringResource(id = R.string.search),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .alpha(alpha)
        ) {
            navController.popBackStack(NavigationScreens.Home.route, inclusive = false, false)
        }

        SearchRoundedBox {
            navController.navigateToSearchScreen()
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.browse_all),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(DataHelper.musicCategories) {
                CategoryWidget(it) {
                    navController.navigateToSearchScreen(it.categoryType)
                }
            }
        }
    }
}

@Composable
private fun SearchRoundedBox(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxWidth()
            .shadow(
                spotColor = White, elevation = 8.dp, shape = CircleShape.copy(CornerSize(20.dp))
            )
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = IconGradient, shape = CircleShape)
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = White
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = stringResource(R.string.search_hint),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400),
                    color = PrimaryLight,
                )
            }
        }
    }
}

@Composable
private fun CategoryWidget(
    musicCategory: MusicCategory,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .shadow(
                spotColor = PrimaryLight, elevation = 8.dp, shape = CircleShape
            )
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = IconGradient, shape = CircleShape)
                .padding(end = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape.copy(CornerSize(26.dp))),
                    painter = painterResource(id = musicCategory.imageDrawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = musicCategory.title,
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500),
                    color = White,
                )
            }
        }
    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchTabScreen()
}

