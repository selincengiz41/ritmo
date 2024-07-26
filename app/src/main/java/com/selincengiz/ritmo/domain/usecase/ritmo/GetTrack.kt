package com.selincengiz.ritmo.domain.usecase.ritmo

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class GetTrack(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String): TrackUI {
        return ritmoRepository.getTrack(id)
    }
}