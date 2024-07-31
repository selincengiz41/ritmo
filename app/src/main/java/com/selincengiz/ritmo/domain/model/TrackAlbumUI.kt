package com.selincengiz.ritmo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackAlbumUI (
    val cover: String?,
    val coverBig: String?,
    val coverMedium: String?,
    val coverSmall: String?,
    val coverXl: String?,
    val id: String?,
    val md5Image: String?,
    val title: String?,
    val tracklist: String?,
    val type: String?
):Parcelable