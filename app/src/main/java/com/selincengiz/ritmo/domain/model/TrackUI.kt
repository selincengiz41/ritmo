package com.selincengiz.ritmo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackUI(
    val album: TrackAlbumUI?,
    val artist: ArtistUI?,
    val duration: String?,
    val explicitContentCover: Int?,
    val explicitContentLyrics: Int?,
    val explicitLyrics: Boolean?,
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
):Parcelable