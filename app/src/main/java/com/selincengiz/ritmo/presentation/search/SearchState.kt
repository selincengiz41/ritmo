package com.selincengiz.ritmo.presentation.search

import androidx.paging.PagingData
import com.selincengiz.ritmo.domain.model.TrackUI
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val ritmo: Flow<PagingData<TrackUI>>? = null,
    val selected: Int = 0,
    val searchHistory: MutableList<String> = emptyList<String>().toMutableList()
)
