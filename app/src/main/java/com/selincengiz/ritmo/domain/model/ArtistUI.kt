package com.selincengiz.ritmo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistUI (
    val id: String?,
    val name: String?,
    val picture: String?,
    val pictureBig: String?,
    val pictureMedium: String?,
    val pictureSmall: String?,
    val pictureXl: String?,
    val tracklist: String?,
    val type: String?
):Parcelable