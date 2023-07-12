package com.example.playerapp.ui.screen.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
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
import com.example.playerapp.ui.model.MusicCategoryType
import com.example.playerapp.ui.theme.PrimaryLight
import com.example.playerapp.ui.theme.White
import com.example.playerapp.utils.IconGradient

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    category: MusicCategoryType? = null,
) {
    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SearchScreenTopBar(
            title = stringResource(id = R.string.search),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .alpha(alpha)
        ) {
            navController.popBackStack()
        }
        SearchRoundedBox {

        }
    }
}

@Composable
private fun SearchRoundedBox(modifier: Modifier = Modifier, onType: (String) -> Unit) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxWidth()
            .shadow(
                spotColor = White, elevation = 8.dp, shape = CircleShape.copy(CornerSize(20.dp))
            )
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

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

