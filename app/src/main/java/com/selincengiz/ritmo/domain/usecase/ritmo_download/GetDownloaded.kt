package com.selincengiz.ritmo.domain.usecase.ritmo_download

import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow

class GetDownloaded(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(): List<String> {
        return ritmoRepository.getDownloaded()
    }
}