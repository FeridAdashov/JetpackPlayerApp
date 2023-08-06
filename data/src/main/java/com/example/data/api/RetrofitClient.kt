package com.example.data.api

import com.example.data.interceptors.HeadersInterceptor
import com.example.data.managers.UserManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.data.Constants
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {

    var service: RetrofitService? = null

    var mOkHttpClient: OkHttpClient = OkHttpClient()

    fun makeRetrofitService(
        isDebug: Boolean,
        userManager: UserManager,
    ): RetrofitService {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        dispatcher.maxRequestsPerHost = 1
        val okHttpClientBuilder = makeOkHttpClientBuilder()

        okHttpClientBuilder.apply {
            dispatcher(dispatcher)
            addInterceptor(HeadersInterceptor(userManager))
            addInterceptor(makeLoggingInterceptor(isDebug))
        }
        service =
            makeRetrofit(
                okHttpClientBuilder.build(),
                makeGson()
            ).create(RetrofitService::class.java)
        return service!!
    }

    private fun makeRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        mOkHttpClient = okHttpClient
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun makeOkHttpClientBuilder(): OkHttpClient.Builder {
        return disableSSLCheck(
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
        )
    }

    private fun disableSSLCheck(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return try {
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?,
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?,
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return emptyArray()
                    }
                }
            )
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}