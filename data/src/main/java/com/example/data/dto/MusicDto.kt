package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class MusicDto(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("desc")
    val desc: String = "",

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageDrawable")
    val imageDrawable: Int? = null,

    @field:SerializedName("category")
    val category: String? = null,
)
