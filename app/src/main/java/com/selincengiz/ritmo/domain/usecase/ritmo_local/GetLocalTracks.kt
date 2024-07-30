package com.selincengiz.ritmo.domain.usecase.ritmo_local

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow

class GetLocalTracks(
    private val ritmoRepository: RitmoRepository
) {
     operator fun invoke(): Flow<List<TrackUI>> {
        return ritmoRepository.getTracksLocal()
    }
}