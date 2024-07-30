package com.selincengiz.ritmo.di

import android.content.Context
import androidx.room.Room
import com.selincengiz.ritmo.data.local.RitmoDao
import com.selincengiz.ritmo.data.local.RitmoDatabase
import com.selincengiz.ritmo.data.local.RitmoTypeConverter
import com.selincengiz.ritmo.util.Constants.RITMO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRitmoDatabase(
        @ApplicationContext context: Context
    ): RitmoDatabase = Room.databaseBuilder(
        context = context,
        klass = RitmoDatabase::class.java,
        name = RITMO_DATABASE
    )
        .addTypeConverter(RitmoTypeConverter())
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideRitmoDao(
        ritmoDatabase: RitmoDatabase
    ): RitmoDao = ritmoDatabase.ritmoDao
}