package com.selincengiz.ritmo.domain.usecase.ritmo_local

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class InsertTrack (
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(track: TrackUI) {
        ritmoRepository.insertTrack(track)
    }
}