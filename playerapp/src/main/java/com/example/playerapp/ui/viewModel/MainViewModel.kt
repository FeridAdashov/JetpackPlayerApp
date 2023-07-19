package com.example.playerapp.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var playerControllerVisibility by mutableStateOf(false)
        private set

    var bottomBarVisibility by mutableStateOf(true)
        private set

    fun changeControllerVisibility(isVisible: Boolean) =
        run { playerControllerVisibility = isVisible }

    fun changeBottomBarVisibility(isVisible: Boolean) =
        run { bottomBarVisibility = isVisible }
}