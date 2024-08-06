package com.selincengiz.ritmo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.selincengiz.ritmo.data.local.entities.DownloadedSong
import com.selincengiz.ritmo.data.local.entities.TrackEntity

@Database(entities = [TrackEntity::class, DownloadedSong::class], version = 2)
@TypeConverters(RitmoTypeConverter::class)
abstract class RitmoDatabase : RoomDatabase() {

    abstract val ritmoDao: RitmoDao
    abstract val downloadedSongDao: DownloadDao

}