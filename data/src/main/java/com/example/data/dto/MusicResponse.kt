package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @field:SerializedName("body")
    val body: NotificationBody? = null,
) : BaseResponse() {
    data class NotificationBody(
        @field:SerializedName("music")
        val music: MusicDto? = null,
    )
}

