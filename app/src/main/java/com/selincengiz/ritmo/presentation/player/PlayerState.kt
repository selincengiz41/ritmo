package com.selincengiz.ritmo.presentation.player

import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI

data class PlayerState(
    val track: TrackUI? = null,
    val trackList: List<TrackUI?>? = emptyList(),
    val isFavorite: Boolean? = null,
    val isDownloaded: Boolean? = null,
    val sideEffect: String? = null,
    val playlists: List<PlaylistUI?> = emptyList()
)