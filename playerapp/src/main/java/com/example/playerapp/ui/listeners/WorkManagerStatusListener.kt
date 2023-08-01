package com.example.playerapp.ui.listeners

interface WorkManagerStatusListener {
    fun success(uri: String, mimeType: String)
    fun failed(message: String)
    fun running()
    fun enqueued(message: String)
    fun blocked(message: String)
    fun canceled(message: String)
}