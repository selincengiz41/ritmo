package com.selincengiz.ritmo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.selincengiz.ritmo.data.local.entities.DownloadedSong

@Dao
interface DownloadDao {
    @Insert
    suspend fun insert(downloadedSong: DownloadedSong): Long

    @Query("SELECT * FROM downloaded_songs")
    suspend fun getAllDownloadedSongs(): List<DownloadedSong>
}