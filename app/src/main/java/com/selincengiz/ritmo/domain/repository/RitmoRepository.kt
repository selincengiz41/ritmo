package com.selincengiz.ritmo.domain.repository

import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI


interface RitmoRepository {
    suspend fun search(
        q: String,
    ): List<TrackUI?>?

    suspend fun getAlbum(
        id: String,
    ): AlbumUI

    suspend fun getTrack(
        id: String,
    ): TrackUI
}