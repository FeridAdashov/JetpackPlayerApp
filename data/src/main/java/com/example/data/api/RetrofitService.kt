package com.example.data.api

import com.example.data.dto.BaseResponse
import com.example.data.dto.MusicListResponse
import com.example.data.dto.MusicResponse
import com.example.domain.entity.Music
import retrofit2.Response
import retrofit2.http.*


interface RetrofitService {

    @GET("/")
    suspend fun getMusicList(): Response<MusicListResponse>

    @GET("/{id}")
    suspend fun getMusicById(@Path("id") id: String): Response<MusicResponse>

    @POST("/")
    suspend fun addMusic(@Body music: Music): Response<BaseResponse>

}
