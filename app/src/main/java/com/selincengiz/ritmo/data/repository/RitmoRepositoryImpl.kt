package com.selincengiz.ritmo.data.repository

import com.selincengiz.ritmo.data.local.RitmoDao
import com.selincengiz.ritmo.data.mapper.Mappers.toAlbumUI
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackEntity
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class RitmoRepositoryImpl(
    private val api: RitmoApi,
    private val dao: RitmoDao
) : RitmoRepository {
    override suspend fun search(q: String): List<TrackUI?>? {
        return api.search(q).data?.map { it?.toTrackUI() }
    }

    override suspend fun getAlbum(id: String): AlbumUI {
        return api.getAlbum(id).toAlbumUI()
    }

    override suspend fun getTrack(id: String): TrackUI {
        return api.getTrack(id).toTrackUI()
    }

    override suspend fun insertTrack(track: TrackUI) {
        dao.insert(track.toTrackEntity())
    }

    override suspend fun deleteTrack(track: TrackUI) {
        dao.delete(track.toTrackEntity())
    }

    override fun getTracksLocal(): Flow<List<TrackUI>> {
         return dao.getTracks().map { it -> it.map { it.toTrackUI() } }
    }

    override suspend fun getTrackLocal(id: String): TrackUI? {
        return dao.getTrack(id)?.toTrackUI()
    }
}