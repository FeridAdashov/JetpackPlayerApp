package com.example.domain.interactors

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.Music
import com.example.domain.entity.MusicEntity
import com.example.domain.entity.MusicListEntity
import com.example.domain.entity.RequestResult
import com.example.domain.repositories.MusicRepository

class MusicInteractor(private val repository: MusicRepository) : BaseInteractor() {

    suspend fun getMusicList(): RequestResult<MusicListEntity> {
        return generateResult(repository.getMusicList())
    }

    suspend fun getMusicById(id: String): RequestResult<MusicEntity> {
        return generateResult(repository.getMusicById(id))
    }

    suspend fun addMusic(music: Music): RequestResult<BaseEntity> {
        return generateResult(repository.addMusic(music))
    }
}