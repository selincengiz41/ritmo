package com.selincengiz.ritmo.presentation.home

import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI

data class HomeState(
    val discover: List<TrackAlbumUI?>? = null,
    val track: TrackUI? = null,
    val artist: List<ArtistUI?>? = null
)