package com.example.data.interceptors

import com.example.data.managers.UserManager
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(private val userManager: UserManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder.apply {
            addHeader("Authorization", userManager.getToken())
            addHeader("Language", userManager.getLanguage() ?: userManager.getDefaultLanguage())
        }
        return chain.proceed(requestBuilder.build())
    }
}

