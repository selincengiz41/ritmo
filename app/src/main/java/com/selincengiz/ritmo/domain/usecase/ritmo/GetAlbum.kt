package com.selincengiz.ritmo.domain.usecase.ritmo

import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class GetAlbum(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String): AlbumUI {
        return ritmoRepository.getAlbum(id)
    }
}