package com.example.data.interceptors

import com.example.data.managers.UserManager
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(private val userManager: UserManager) : Interceptor {

    private val token = "Bearer token12345"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder.apply {
            addHeader("Authorization", token)
            addHeader("Language", userManager.getLanguage() ?: userManager.getDefaultLanguage())
        }
        return chain.proceed(requestBuilder.build())
    }
}

