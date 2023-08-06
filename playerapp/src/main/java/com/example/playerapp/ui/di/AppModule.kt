package com.example.playerapp.ui.di

import android.content.Context
import com.example.data.api.RetrofitClient
import com.example.data.api.RetrofitService
import com.example.data.managers.UserManager
import com.example.playerapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitService(
        userManager: UserManager,
    ): RetrofitService {
        return RetrofitClient.makeRetrofitService(BuildConfig.DEBUG, userManager)
    }

    @Provides
    @Singleton
    fun provideUserManager(@ApplicationContext context: Context): UserManager {
        return UserManager(context)
    }
}