package com.selincengiz.ritmo.presentation.artist

import com.selincengiz.ritmo.domain.model.ArtistUI


sealed class ArtistEvent {
    data class GetArtist(val artist: ArtistUI) : ArtistEvent()
}