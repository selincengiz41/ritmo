package com.selincengiz.ritmo.domain.usecase.ritmo_firebase

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class AddToPlaylist(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String, trackUI: TrackUI) {
        return ritmoRepository.addTrackToPlaylist(playlistId = id, track = trackUI)
    }
}