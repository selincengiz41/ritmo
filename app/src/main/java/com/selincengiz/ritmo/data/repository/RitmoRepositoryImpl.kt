package com.selincengiz.ritmo.data.repository

import com.selincengiz.ritmo.data.mapper.Mappers.toAlbumUI
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class RitmoRepositoryImpl(
    private val api: RitmoApi
) : RitmoRepository {
    override suspend fun search(q: String): List<TrackUI?>? {
        return api.search(q).data?.map { it?.toTrackUI() }
    }

    override suspend fun getAlbum(id: Int): AlbumUI {
        return api.getAlbum(id).toAlbumUI()
    }

    override suspend fun getTrack(id: Int): TrackUI {
        return api.getTrack(id).toTrackUI()
    }
}