package com.example.playerapp.ui.di

import com.example.data.api.RetrofitService
import com.example.data.mappers.MusicMapper
import com.example.data.repositories.MusicRepositoryImpl
import com.example.domain.interactors.MusicInteractor
import com.example.domain.repositories.MusicRepository
import com.example.playerapp.ui.viewModel.MusicViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MusicModule {

    @Singleton
    @Provides
    fun provideMusicInteractor(authRepository: MusicRepository): MusicInteractor {
        return MusicInteractor(authRepository)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        apiService: RetrofitService,
        authMapper: MusicMapper
    ): MusicRepository {
        return MusicRepositoryImpl(apiService, authMapper)
    }

    @Singleton
    @Provides
    fun provideMapper(): MusicMapper = MusicMapper()

    @Provides
    @Singleton
    fun provideMusicViewModel(interactor: MusicInteractor): MusicViewModel {
        return MusicViewModel(interactor)
    }
}