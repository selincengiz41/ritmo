package com.selincengiz.ritmo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistUI(
    val name: String,
    val tracks: MutableList<TrackUI>
):Parcelable
