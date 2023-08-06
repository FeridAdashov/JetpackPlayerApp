package com.example.playerapp.ui.screen.homeScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.domain.entity.Music
import com.example.domain.entity.MusicCategoryType
import com.example.domain.entity.RequestResult
import com.example.playerapp.R
import com.example.playerapp.ui.theme.RedDark
import com.example.playerapp.ui.theme.TitleGray
import com.example.playerapp.ui.theme.White
import com.example.playerapp.ui.viewModel.MainViewModel
import com.example.playerapp.ui.viewModel.MusicViewModel

@Composable
fun AddMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val musicViewModel: MusicViewModel = hiltViewModel()

    val title = remember { mutableStateOf("") }
    val musicUrl = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mainViewModel.changeControllerVisibility(false)
        mainViewModel.changeBottomBarVisibility(false)
    }

    fun onBackPressed() {
        mainViewModel.changeControllerVisibility(true)
        mainViewModel.changeBottomBarVisibility(true)
        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    musicViewModel.addMusicLiveData.observe(LocalLifecycleOwner.current) {
        if (it is RequestResult.Error) {
            Toast.makeText(context, it.message ?: "Error happened", Toast.LENGTH_SHORT).show()
        } else if (it is RequestResult.Success) {
            musicViewModel.getMusicList()
            Toast.makeText(context, "Music added", Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    fun editText(state: MutableState<String>, placeholder: String) {
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value,
            onValueChange = { state.value = it },
            placeholder = { Text(text = placeholder, color = TitleGray) },
            singleLine = true,
            textStyle = TextStyle(color = White),
        )
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        editText(state = title, placeholder = "Title")
        editText(state = musicUrl, placeholder = "Music url")
        editText(state = imageUrl, placeholder = "Image url")
        editText(state = category, placeholder = "Category")
        editText(state = desc, placeholder = "Description")

        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            Arrangement.spacedBy(16.dp, Alignment.End),
        ) {
            Button(
                colors = ButtonDefaults.elevatedButtonColors(containerColor = RedDark),
                onClick = { onBackPressed() }) {
                Text(text = stringResource(R.string.cancel), color = White)
            }

            Button(onClick = {
                if (title.value.isEmpty()) {
                    Toast.makeText(context, "Type title", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (musicUrl.value.isEmpty()) {
                    Toast.makeText(context, "Type music url", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (imageUrl.value.isEmpty()) {
                    Toast.makeText(context, "Type image url", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                musicViewModel.addMusic(
                    Music(
                        title = title.value,
                        url = musicUrl.value,
                        imageUrl = imageUrl.value,
                        category = MusicCategoryType.valueOf(category.value.uppercase()),
                        desc = desc.value,
                    )
                )
            }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

@Preview
@Composable
fun AddMusicScreenPreview() {
    AddMusicScreen()
}
