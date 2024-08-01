package com.selincengiz.ritmo.presentation.player



sealed class PlayerEvent {
    data class GetTrack(val id: String) : PlayerEvent()
    data object InsertDeleteTrack : PlayerEvent()
    data object GetPlaylists:PlayerEvent()
    data class AddToPlaylist(val playlistId: String) : PlayerEvent()
}