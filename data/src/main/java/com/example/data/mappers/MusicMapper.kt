package com.example.data.mappers

import com.example.data.dto.MusicDto
import com.example.data.dto.MusicListResponse
import com.example.data.dto.MusicResponse
import com.example.domain.entity.Music
import com.example.domain.entity.MusicCategoryType
import com.example.domain.entity.MusicEntity
import com.example.domain.entity.MusicListEntity

class MusicMapper : BaseMapper() {

    fun toMusicListEntity(response: MusicListResponse): MusicListEntity {
        return MusicListEntity(
            code = response.code,
            message = response.message,
            musicList = response.body?.musicList?.map { toMusic(it) },
        )
    }

    fun toMusicEntity(it: MusicResponse): MusicEntity {
        return MusicEntity(
            code = it.code,
            message = it.message,
            music = toMusic(it.body?.music)
        )
    }

    private fun toMusic(it: MusicDto?): Music {
        return Music(
            id = it?.id,
            title = it?.title ?: "",
            url = it?.url ?: "",
            desc = it?.desc ?: "",
            imageUrl = it?.imageUrl,
            imageDrawable = it?.imageDrawable,
            category = MusicCategoryType.valueOf(it?.category?.uppercase() ?: ""),
        )
    }
}