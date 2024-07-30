package com.selincengiz.ritmo.presentation.favorite

import com.selincengiz.ritmo.domain.model.TrackUI

data class FavoriteState(
    val favoriteRitmo: List<TrackUI?>? = null
)