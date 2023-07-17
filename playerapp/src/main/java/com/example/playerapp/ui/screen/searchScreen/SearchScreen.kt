package com.example.playerapp.ui.screen.searchScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicCategoryType
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.utils.DataHelper
import com.example.playerapp.utils.IconGradient

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    category: MusicCategoryType? = null,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val alpha = if (scrollState.value > 100) 0f else (100 - scrollState.value) / 100f

    val searchedList = remember {
        mutableStateOf(if (category == null) DataHelper.recentlyPlayedMusicList
        else DataHelper.recentlyPlayedMusicList.filter { it.category == category })
    }

    LaunchedEffect(Unit) {
        mainViewModel.changeControllerVisibility(false)
        mainViewModel.changeBottomBarVisibility(false)
    }

    fun onBackPressed() {
        mainViewModel.changeControllerVisibility(true)
        mainViewModel.changeBottomBarVisibility(true)
        navController.popBackStack()
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        SearchScreenTopBar(
            title = stringResource(id = R.string.search),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .alpha(alpha)
        ) {
            onBackPressed()
        }
        SearchRoundedBoxTextField(category = category) {
            searchedList.value =
                DataHelper.recentlyPlayedMusicList.filter { m -> m.title.contains(it, true) }
        }
        LazyColumn(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 31.dp)
        ) {
            items(searchedList.value) {
                SearchItemView(music = it)
            }
        }
    }
}

@Composable
private fun SearchItemView(modifier: Modifier = Modifier, music: Music) {
    Row(
        modifier = modifier.clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        music.imageDrawable?.let { img ->
            Image(
                painter = painterResource(img),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = music.title,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = music.desc,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500),
                color = Color(0xFF7F8489),
                textAlign = TextAlign.Center
            )
        }
        IconButton(onClick = {}, modifier = Modifier.size(20.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = null,
                tint = White
            )
        }
    }
}

@Composable
private fun SearchRoundedBoxTextField(
    modifier: Modifier = Modifier,
    category: MusicCategoryType? = null,
    onType: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var s = ""
    category?.value?.let {
        s = "Category: $it"
    }
    var text by remember { mutableStateOf(TextFieldValue(s)) }

    Box(
        modifier = modifier
            .padding(start = 16.dp, top = 30.dp, end = 16.dp)
            .fillMaxWidth()
            .shadow(
                spotColor = White, elevation = 8.dp, shape = CircleShape.copy(CornerSize(20.dp))
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = IconGradient, shape = CircleShape)
                .padding(horizontal = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = White
                )

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    value = text,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = White
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    label = { Text(text = stringResource(id = R.string.search_hint)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = text.text.isNotEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(
                                onClick = {
                                    text = TextFieldValue()
                                    onType.invoke(text.text)
                                },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = null,
                                    tint = White
                                )
                            }
                        }
                    },
                    onValueChange = {
                        text = it
                        onType.invoke(text.text)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(mainViewModel = MainViewModel())
}

