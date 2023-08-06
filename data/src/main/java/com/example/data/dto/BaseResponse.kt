package com.example.data.dto

import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection

open class BaseResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("code")
    val code: Int = HttpURLConnection.HTTP_OK,
)