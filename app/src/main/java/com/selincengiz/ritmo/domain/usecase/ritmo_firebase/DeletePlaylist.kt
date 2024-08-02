package com.selincengiz.ritmo.domain.usecase.ritmo_firebase

import com.selincengiz.ritmo.domain.repository.RitmoRepository

class DeletePlaylist(
    private val ritmoRepository: RitmoRepository
) {
    operator fun invoke(id: String) {
        ritmoRepository.deletePlaylist(id)
    }
}