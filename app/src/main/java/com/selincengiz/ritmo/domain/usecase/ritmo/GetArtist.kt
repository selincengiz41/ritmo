package com.selincengiz.ritmo.domain.usecase.ritmo

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow

class GetArtist(
    private val ritmoRepository: RitmoRepository
) {
    suspend operator fun invoke(id: String): Flow<PagingData<TrackUI>> {
        return ritmoRepository.getArtist(id)
    }
}