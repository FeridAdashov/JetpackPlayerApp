package com.example.domain.entity


class MusicListEntity(
    code: Int,
    message: String? = null,
    val musicList: List<Music>? = null
) : BaseEntity(code, message)