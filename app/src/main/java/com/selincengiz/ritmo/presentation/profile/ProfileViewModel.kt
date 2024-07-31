package com.selincengiz.ritmo.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.selincengiz.ritmo.domain.model.ListTrackUI
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        auth.currentUser?.photoUrl?.let {
            _state.value = state.value.copy(image = it)
        }
        getPlaylists()
        Log.i("getPlaylists", _state.value.playlists.toString())
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
            }
        }
    }

    private fun createPlayList(name: String) {
        val playlistsRef =
            db.collection("users").document(auth.currentUser!!.uid).collection("playlists")

        val playlistData = mapOf(
            "name" to name,
            "tracks" to listOf<TrackUI>() // Çalma listesine eklemek istediğiniz şarkıların listesi
        )

        playlistsRef.add(playlistData)
            .addOnSuccessListener {
                // Başarılı
                println("Playlist successfully added!")
            }
            .addOnFailureListener { e ->
                // Hata
                println("Error adding playlist: $e")
            }
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

    private fun getPlaylists() {
        val playlistsRef =
            db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
        try {
            playlistsRef.addSnapshotListener { playlistsSnapshot, e ->
                val templist = mutableListOf<PlaylistUI?>()

                playlistsSnapshot?.forEach { doc ->
                    templist.add(
                        PlaylistUI(
                            id = doc.id,
                            name = doc.get("name") as String,
                            tracks = doc.toObject(ListTrackUI::class.java).listTrack.toMutableList()
                        )
                    )
                }
                _state.value = state.value.copy(playlists = templist)
            }
        } catch (e: Exception) {
            // Hata yönetimi
            Log.i("getPlaylists", e.message.toString())
        }
    }

}
