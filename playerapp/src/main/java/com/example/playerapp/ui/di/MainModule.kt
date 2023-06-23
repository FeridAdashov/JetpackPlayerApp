package com.example.playerapp.ui.di

import com.example.playerapp.ui.viewModel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainViewModel(): MainViewModel {
        return MainViewModel()
    }
}