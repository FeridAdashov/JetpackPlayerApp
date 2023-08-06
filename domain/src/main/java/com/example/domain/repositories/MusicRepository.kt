package com.example.domain.repositories

import com.example.domain.entity.BaseEntity
import com.example.domain.entity.Music
import com.example.domain.entity.MusicEntity
import com.example.domain.entity.MusicListEntity

interface MusicRepository {

    /**
     * Get all music
     * @return BaseEntity
     */
    suspend fun getMusicList(): MusicListEntity

    /**
     * Get music by id
     * @param id
     * @return BaseEntity
     */
    suspend fun getMusicById(id: String): MusicEntity

    /**
     * Add music
     * @return BaseEntity
     */
    suspend fun addMusic(music: Music): BaseEntity

}