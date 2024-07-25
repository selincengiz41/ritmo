package com.selincengiz.ritmo.domain.usecase.ritmo

import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository

class Search(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(q: String): List<TrackUI?>? {
        return ritmoRepository.search(q)
    }
}