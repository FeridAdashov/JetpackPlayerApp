package com.example.domain.entity

data class Music(
    val id: String? = null,
    val title: String,
    val url: String,
    val desc: String = "",
    val imageUrl: String? = null,
    val imageDrawable: Int? = null,
    val category: MusicCategoryType = MusicCategoryType.UNKNOWN
)
