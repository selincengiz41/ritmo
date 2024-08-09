package com.selincengiz.ritmo.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.selincengiz.ritmo.data.local.DownloadDao
import com.selincengiz.ritmo.data.local.RitmoDao
import com.selincengiz.ritmo.data.manager.LocalUserManagerImpl
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.data.repository.RitmoRepositoryImpl
import com.selincengiz.ritmo.domain.manager.LocalUserManager
import com.selincengiz.ritmo.domain.repository.RitmoRepository
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
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideRitmoRepository(
        ritmoApi: RitmoApi,
        ritmoDao: RitmoDao,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        ritmoDaoDownloaded: DownloadDao
    ): RitmoRepository = RitmoRepositoryImpl(
        api = ritmoApi,
        dao = ritmoDao,
        daoDownloaded = ritmoDaoDownloaded,
        firestore = firestore,
        auth = auth
    )
}