package com.example.playerapp.ui.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.domain.entity.Music
import com.rcudev.player_service.service.PlayerEvent
import com.rcudev.player_service.service.SimpleMediaServiceHandler
import com.rcudev.player_service.service.SimpleMediaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SimpleMediaViewModel @Inject constructor(
    private val simpleMediaServiceHandler: SimpleMediaServiceHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableLongStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableFloatStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            simpleMediaServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is SimpleMediaState.Buffering -> {
                        _uiState.value = UIState.Buffering
                        calculateProgressValues(mediaState.progress)
                    }

                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
                    is SimpleMediaState.Playing -> {
                        isPlaying = mediaState.isPlaying
                    }

                    is SimpleMediaState.Progress -> {
                        calculateProgressValues(mediaState.progress)
                    }

                    is SimpleMediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }

                    is SimpleMediaState.Error -> {
                        _uiState.value = UIState.Error(mediaState.message)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Previous -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Previous)
            UIEvent.Next -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Next)
            UIEvent.Play -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Play)
            UIEvent.Pause -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Pause)
            UIEvent.Stop -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
            is UIEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                simpleMediaServiceHandler.onPlayerEvent(
                    PlayerEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }
        }
    }

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }

    fun setMusicList(musicList: List<Music>) {
        val mediaItemList = mutableListOf<MediaItem>()
        musicList.forEach {
            mediaItemList.add(
                MediaItem.Builder()
                    .setUri(it.url)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                            .setArtworkUri(Uri.parse(it.imageUrl))
                            .setAlbumTitle(it.title)
                            .setDisplayTitle(it.desc)
                            .build()
                    ).build()
            )
        }

        simpleMediaServiceHandler.addMediaItemList(mediaItemList)
    }

    fun setMusic(music: Music) {
        val mediaData = MediaMetadata.Builder().apply {
            if (!music.imageUrl.isNullOrBlank())
                setArtworkUri(Uri.parse(music.imageUrl))
            setAlbumTitle(music.title)
            setDisplayTitle(music.desc)
        }.build()

        val mediaItem = MediaItem.Builder()
            .setUri(music.url)
            .setMediaMetadata(mediaData)
            .build()

        simpleMediaServiceHandler.addMediaItem(mediaItem)
    }
}

sealed class UIEvent {
    object Play : UIEvent()
    object Pause : UIEvent()
    object Stop : UIEvent()
    object Previous : UIEvent()
    object Next : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
    object Buffering : UIState()
    data class Error(val message: String) : UIState()
}