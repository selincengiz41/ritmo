package com.selincengiz.ritmo.presentation.artist

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import kotlinx.coroutines.flow.Flow

data class ArtistState(
    val artistTracks: Flow<PagingData<TrackUI>>? = null,
    val artist: ArtistUI? = null
)