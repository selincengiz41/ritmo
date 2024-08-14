package com.selincengiz.ritmo.presentation.profile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.RitmoFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val ritmoFirebaseUseCase: RitmoFirebaseUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        auth.currentUser?.photoUrl?.let {
            _state.value = state.value.copy(image = it)
        }
        auth.currentUser?.displayName?.let {
            _state.value = state.value.copy(name = it)
        }
        getPlaylists()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Logout -> {
                auth.signOut()
            }

            is ProfileEvent.PickImage -> {
                pickImage(event.uri)
            }

            is ProfileEvent.CreatePlayList -> {
                createPlayList(event.name)
                getPlaylists()
            }

            is ProfileEvent.DeletePlaylist -> {
                ritmoFirebaseUseCase.deletePlaylist(event.id)
                getPlaylists()
            }
        }
    }

    private fun getPlaylists() {
        viewModelScope.launch {
            val playlists = ritmoFirebaseUseCase.getPlaylists()
            _state.value = state.value.copy(playlists = playlists)
        }
    }

    private fun createPlayList(name: String) {
        ritmoFirebaseUseCase.createPlaylist(name = name)
    }

    private fun pickImage(uri: Uri) {
        _state.value = state.value.copy(image = uri)
        val profileUpdates = userProfileChangeRequest {
            photoUri = _state.value.image
        }
        auth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                task.addOnSuccessListener {
                    _state.value = state.value.copy(sideEffect = "Successfully updated profile!")
                }
                task.addOnFailureListener {
                    _state.value = state.value.copy(sideEffect = "Profile update is failed!")
                }
            }
    }
}
