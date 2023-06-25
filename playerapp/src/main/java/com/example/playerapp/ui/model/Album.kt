package com.example.playerapp.ui.model

data class Album(
    val artistName: String,
    val monthlyListenersCount: Int? = null,
    val worldPlace: Int? = null,
    val bio: String? = null,
    val posterUrl: String? = null,
    val posterDrawable: Int? = null,
    val artistUrl: String? = null,
    val artistDrawable: Int? = null,
)
