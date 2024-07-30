package com.selincengiz.ritmo.domain.usecase.ritmo_local

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class GetLocalTrack(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String): TrackUI? {
        return ritmoRepository.getTrackLocal(id)
    }
}