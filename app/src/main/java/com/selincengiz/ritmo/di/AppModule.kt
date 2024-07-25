package com.selincengiz.ritmo.di

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.selincengiz.ritmo.data.manager.LocalUserManagerImpl
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.data.repository.RitmoRepositoryImpl
import com.selincengiz.ritmo.domain.manager.LocalUserManager
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import com.selincengiz.ritmo.domain.usecase.app_entry.AppEntryUseCase
import com.selincengiz.ritmo.domain.usecase.app_entry.ReadAppEntry
import com.selincengiz.ritmo.domain.usecase.app_entry.SaveAppEntry
import com.selincengiz.ritmo.domain.usecase.ritmo.GetAlbum
import com.selincengiz.ritmo.domain.usecase.ritmo.GetTrack
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo.Search
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
    fun provideLocalUserManager(
        @ApplicationContext context: Context
    ): LocalUserManager = LocalUserManagerImpl(context)

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideAppEntryUseCase(
        localUserManager: LocalUserManager
    ) = AppEntryUseCase(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideRitmoRepository(
        ritmoApi: RitmoApi
    ): RitmoRepository = RitmoRepositoryImpl(
        api = ritmoApi
    )

    @Provides
    @Singleton
    fun provideRitmoUseCase(
        ritmoRepository: RitmoRepository
    ) = RitmoUseCase(
        getAlbum = GetAlbum(ritmoRepository),
        getTrack = GetTrack(ritmoRepository),
        search = Search(ritmoRepository)
    )
}