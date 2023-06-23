package com.example.playerapp.ui.model

data class MusicEvent(
    val title: String,
    val type: MusicEventType,
    val desc: String? = null,
    val text: String? = null,
    val imageUrl: String? = null,
    val imageDrawable: Int? = null,
)
