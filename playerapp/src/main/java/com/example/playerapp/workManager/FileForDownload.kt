package com.example.playerapp.workManager

data class FileForDownload(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    var downloadedUri: String? = null,
    var isDownloading: Boolean = false,
)