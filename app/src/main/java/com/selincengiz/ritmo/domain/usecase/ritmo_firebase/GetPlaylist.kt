package com.selincengiz.ritmo.domain.usecase.ritmo_firebase

import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class GetPlaylist(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String): PlaylistUI? {
        return ritmoRepository.getPlaylist(id)
    }
}