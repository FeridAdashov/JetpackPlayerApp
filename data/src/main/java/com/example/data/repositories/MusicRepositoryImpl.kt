package com.example.data.repositories

import com.example.data.api.RetrofitService
import com.example.data.dto.BaseResponse
import com.example.data.dto.MusicListResponse
import com.example.data.dto.MusicResponse
import com.example.data.managers.ExceptionUtils
import com.example.data.mappers.MusicMapper
import com.example.domain.entity.BaseEntity
import com.example.domain.entity.Music
import com.example.domain.entity.MusicEntity
import com.example.domain.entity.MusicListEntity
import com.example.domain.repositories.MusicRepository
import com.google.gson.Gson

class MusicRepositoryImpl(
    private val apiService: RetrofitService,
    private val musicMapper: MusicMapper,
) : MusicRepository {
    override suspend fun getMusicList(): MusicListEntity {
        return try {
            val response = apiService.getMusicList()

            if (response.isSuccessful) {
                musicMapper.toMusicListEntity(response.body()!!)
            } else {
                val errorResponse = Gson().fromJson(
                    response.errorBody()?.string(),
                    MusicListResponse::class.java
                )
                musicMapper.toMusicListEntity(errorResponse)
            }
        } catch (e: Exception) {
            return MusicListEntity(
                code = ExceptionUtils.getExceptionCode(e),
                message = e.message
            )
        }
    }

    override suspend fun getMusicById(id: String): MusicEntity {
        return try {
            val response = apiService.getMusicById(id)

            if (response.isSuccessful) {
                musicMapper.toMusicEntity(response.body()!!)
            } else {
                val errorResponse = Gson().fromJson(
                    response.errorBody()?.string(),
                    MusicResponse::class.java
                )
                musicMapper.toMusicEntity(errorResponse)
            }
        } catch (e: Exception) {
            return MusicEntity(
                code = ExceptionUtils.getExceptionCode(e),
                message = e.message
            )
        }
    }

    override suspend fun addMusic(music: Music): BaseEntity {
        return try {
            val response = apiService.addMusic(music)

            if (response.isSuccessful) {
                musicMapper.toBaseEntity(response.body()!!)
            } else {
                val errorResponse = Gson().fromJson(
                    response.errorBody()?.string(),
                    BaseResponse::class.java
                )
                musicMapper.toBaseEntity(errorResponse)
            }
        } catch (e: Exception) {
            return BaseEntity(
                code = ExceptionUtils.getExceptionCode(e),
                message = e.message
            )
        }
    }
}