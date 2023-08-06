package com.example.domain.entity


class MusicEntity(
    code: Int,
    message: String? = null,
    val music: Music? = null
) : BaseEntity(code, message)