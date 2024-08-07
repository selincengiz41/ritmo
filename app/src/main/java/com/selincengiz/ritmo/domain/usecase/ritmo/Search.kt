package com.selincengiz.ritmo.domain.usecase.ritmo

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow

class Search(
    private val ritmoRepository: RitmoRepository
) {
     operator fun invoke(q: String): Flow<PagingData<TrackUI>> {
        return ritmoRepository.search(q)
    }
}