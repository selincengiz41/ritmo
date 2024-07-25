package com.selincengiz.ritmo.presentation.search

import com.selincengiz.ritmo.domain.model.TrackUI

data class SearchState(
    val searchQuery: String = "",
    val ritmo: List<TrackUI?>? = null,
    val selected: Int = 0,
    val searchHistory: MutableList<String> = emptyList<String>().toMutableList()
)
