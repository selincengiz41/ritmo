package com.selincengiz.ritmo.presentation.detail

import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.PlaylistUI

data class DetailState(
    val album: AlbumUI? = null,
    val playlist: PlaylistUI? = null
)