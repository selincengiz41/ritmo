package com.selincengiz.ritmo.presentation.profile

import android.net.Uri
import com.selincengiz.ritmo.domain.model.PlaylistUI


data class ProfileState(
    val sideEffect: String? = null,
    val image: Uri? = null,
    val name: String? = null,
    val playlists: List<PlaylistUI?> = emptyList()
)