package com.selincengiz.ritmo.presentation.player

import com.selincengiz.ritmo.domain.model.TrackUI

data class PlayerState(
    val track: TrackUI? = null,
    val isFavorite: Boolean? = null,
    val sideEffect: String? = null
)