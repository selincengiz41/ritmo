package com.selincengiz.ritmo.domain.repository

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.DownloadedUI
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import kotlinx.coroutines.flow.Flow


interface RitmoRepository {
     fun search(
        q: String,
    ):  Flow<PagingData<TrackUI>>

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

    suspend fun getPlaylists(): List<PlaylistUI?>

    suspend fun getPlaylist(id: String): PlaylistUI?

    fun createPlaylist(name: String)

    fun deletePlaylist(id: String)

    suspend fun addTrackToPlaylist(playlistId: String, track: TrackUI)

    suspend fun insertDownloaded(id: String)

    suspend fun getDownloaded(): List<String>

}