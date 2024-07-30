package com.selincengiz.ritmo.domain.repository

import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI
import kotlinx.coroutines.flow.Flow


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

    suspend fun insertTrack(track: TrackUI)

    suspend fun deleteTrack(track: TrackUI)

    fun getTracksLocal(): Flow<List<TrackUI>>

    suspend fun getTrackLocal(id: String): TrackUI?
}