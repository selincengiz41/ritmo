package com.selincengiz.ritmo.di

import com.selincengiz.ritmo.domain.manager.LocalUserManager
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import com.selincengiz.ritmo.domain.usecase.app_entry.AppEntryUseCase
import com.selincengiz.ritmo.domain.usecase.app_entry.ReadAppEntry
import com.selincengiz.ritmo.domain.usecase.app_entry.SaveAppEntry
import com.selincengiz.ritmo.domain.usecase.ritmo.GetAlbum
import com.selincengiz.ritmo.domain.usecase.ritmo.GetTrack
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo.Search
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.AddToPlaylist
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.CreatePlaylists
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.GetPlaylists
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.RitmoFirebaseUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.DeleteTrack
import com.selincengiz.ritmo.domain.usecase.ritmo_local.GetLocalTrack
import com.selincengiz.ritmo.domain.usecase.ritmo_local.GetLocalTracks
import com.selincengiz.ritmo.domain.usecase.ritmo_local.InsertTrack
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

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
    fun provideRitmoUseCase(
        ritmoRepository: RitmoRepository
    ) = RitmoUseCase(
        getAlbum = GetAlbum(ritmoRepository),
        getTrack = GetTrack(ritmoRepository),
        search = Search(ritmoRepository)
    )

    @Provides
    @Singleton
    fun provideRitmoLocalUseCase(
        ritmoRepository: RitmoRepository
    ) = RitmoLocalUseCase(
        insertTrack = InsertTrack(ritmoRepository),
        deleteTrack = DeleteTrack(ritmoRepository),
        getLocalTracks = GetLocalTracks(ritmoRepository),
        getLocalTrack = GetLocalTrack(ritmoRepository)
    )

    @Provides
    @Singleton
    fun provideRitmoFirebaseUseCase(
        ritmoRepository: RitmoRepository
    ) = RitmoFirebaseUseCase(
        getPlaylists = GetPlaylists(ritmoRepository),
        createPlaylist = CreatePlaylists(ritmoRepository),
        addPlaylist = AddToPlaylist(ritmoRepository)
    )
}