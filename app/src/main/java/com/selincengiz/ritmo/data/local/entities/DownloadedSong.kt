package com.selincengiz.ritmo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("downloaded_songs")
data class DownloadedSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val songId: String
)
