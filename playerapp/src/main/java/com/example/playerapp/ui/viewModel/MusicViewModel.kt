package com.example.playerapp.ui.viewModel

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.Music
import com.example.domain.entity.MusicEntity
import com.example.domain.entity.MusicListEntity
import com.example.domain.entity.RequestResult
import com.example.domain.interactors.MusicInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class MusicViewModel @Inject constructor(private val musicInteractor: MusicInteractor) :
    BaseViewModel() {

    var musicListLiveData = MutableStateFlow<RequestResult<MusicListEntity>?>(null)
        private set
    var musicByIdLiveData = MutableStateFlow<RequestResult<MusicEntity>?>(null)
        private set
    var addMusicLiveData = SingleLiveEvent<RequestResult<BaseEntity>>()
        private set

    private var mMusicListJob: Job? = null
    private var mMusicJob: Job? = null
    private var mAddMusicJob: Job? = null

    fun getMusicList() {
        mMusicListJob?.cancel()
        mMusicListJob = sendRequest({ musicInteractor.getMusicList() }, {
            musicListLiveData.value = it
        })
    }

    fun getMusicById(id: String) {
        mMusicJob?.cancel()
        mMusicJob = sendRequest({ musicInteractor.getMusicById(id) }, {
            musicByIdLiveData.value = it
        })
    }

    fun addMusic(music: Music) {
        mAddMusicJob?.cancel()
        mAddMusicJob = sendRequest({ musicInteractor.addMusic(music) }, {
            addMusicLiveData.postValue(it)
        })
    }
}