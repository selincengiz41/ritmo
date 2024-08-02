package com.selincengiz.ritmo.domain.usecase.ritmo_firebase

data class RitmoFirebaseUseCase(
    val addPlaylist: AddToPlaylist,
    val createPlaylist: CreatePlaylists,
    val getPlaylists: GetPlaylists,
    val getPlaylist: GetPlaylist,
    val deletePlaylist: DeletePlaylist
)