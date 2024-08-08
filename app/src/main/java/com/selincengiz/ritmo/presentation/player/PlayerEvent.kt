package com.selincengiz.ritmo.presentation.player

import com.selincengiz.ritmo.domain.model.TrackUI


sealed class PlayerEvent {
    data object InsertDeleteTrack : PlayerEvent()
    data object GetPlaylists : PlayerEvent()
    data class AddToPlaylist(val playlistId: String) : PlayerEvent()
    data class DeletePlaylist(val id: String) : PlayerEvent()
    data class UpdateTrack(val track: List<TrackUI?>?, val index: Int?) : PlayerEvent()
    data class Download(val id: String) : PlayerEvent()
    data class ChangeSong(val index: Int) : PlayerEvent()
}