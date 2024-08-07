package com.selincengiz.ritmo.presentation.home

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val discover: Flow<PagingData<TrackAlbumUI>>? = null,
    val track: Flow<PagingData<TrackUI>>? = null,
    val artist:Flow<PagingData<ArtistUI>>? = null,
    val isFavorite: Boolean? = null
)