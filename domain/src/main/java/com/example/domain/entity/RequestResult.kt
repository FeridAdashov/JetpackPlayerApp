package com.example.domain.entity

sealed class RequestResult<out T> {

    data class Success<out R>(
        val code: Int,
        val body: R,
    ) : RequestResult<R>()

    data class Error<out R>(
        val code: Int,
        val message: String?,
    ) : RequestResult<R>()
}