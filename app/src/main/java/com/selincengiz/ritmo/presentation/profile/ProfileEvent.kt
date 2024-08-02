package com.selincengiz.ritmo.presentation.profile

import android.net.Uri


sealed class ProfileEvent {
    data object Logout : ProfileEvent()
    data class PickImage(val uri: Uri) : ProfileEvent()
    data class CreatePlayList(val name: String) : ProfileEvent()
    data class DeletePlaylist(val id: String) : ProfileEvent()
}