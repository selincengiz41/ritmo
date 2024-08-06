package com.selincengiz.ritmo.domain.usecase.ritmo_download

import com.selincengiz.ritmo.domain.repository.RitmoRepository

class InsertDownloaded (
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String) {
        ritmoRepository.insertDownloaded(id)
    }
}