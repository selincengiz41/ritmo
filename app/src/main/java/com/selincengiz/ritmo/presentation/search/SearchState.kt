package com.selincengiz.ritmo.presentation.search

import com.selincengiz.ritmo.domain.model.TrackUI

data class SearchState(
    val searchQuery: String = "",
    val ritmo: List<TrackUI?>? = null,
)
