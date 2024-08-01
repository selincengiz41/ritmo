package com.selincengiz.ritmo.domain.usecase.ritmo_firebase

import com.selincengiz.ritmo.domain.repository.RitmoRepository

class CreatePlaylists(
    private val ritmoRepository: RitmoRepository
) {
    operator fun invoke(name: String) {
        ritmoRepository.createPlaylist(name = name)
    }
}