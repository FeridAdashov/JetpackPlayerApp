package com.example.playerapp.ui.model

data class FileForDownload(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    var downloadedUri: String? = null,
    var isDownloading: Boolean = false,
)