package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class MusicListResponse(
    @field:SerializedName("body")
    val body: MusicListResponseBody? = null,
) : BaseResponse() {
    data class MusicListResponseBody(
        @field:SerializedName("musicList")
        val musicList: List<MusicDto>? = null,
    )
}

