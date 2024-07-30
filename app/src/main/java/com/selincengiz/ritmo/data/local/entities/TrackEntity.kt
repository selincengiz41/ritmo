package com.selincengiz.ritmo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI

@Entity("Tracks")
data class TrackEntity(
    val albumEntity: TrackAlbumEntity?,
    val artistEntity: ArtistEntity?,
    val duration: String?,
    val explicitContentCover: Int?,
    val explicitContentLyrics: Int?,
    val explicitLyrics: Boolean?,
    @PrimaryKey
    val id: String?,
    val link: String?,
    val md5Image: String?,
    val preview: String?,
    val rank: String?,
    val readable: Boolean?,
    val title: String?,
    val titleShort: String?,
    val titleVersion: String?,
    val type: String?
)
