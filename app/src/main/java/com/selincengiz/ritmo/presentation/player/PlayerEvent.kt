package com.selincengiz.ritmo.presentation.player

import com.selincengiz.ritmo.domain.model.TrackUI


sealed class PlayerEvent {
    data class GetTrack(val id: String) : PlayerEvent()
    data object InsertDeleteTrack : PlayerEvent()
    data object GetPlaylists : PlayerEvent()
    data class AddToPlaylist(val playlistId: String) : PlayerEvent()
    data class DeletePlaylist(val id: String) : PlayerEvent()
    data class UpdateTrack(val track: TrackUI) : PlayerEvent()
    data class Download(val id: String) : PlayerEvent()
}