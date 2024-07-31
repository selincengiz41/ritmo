package com.selincengiz.ritmo.presentation.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.GetAlbum -> {
                getAlbum(event.id)
            }

            is DetailsEvent.GetPlaylist -> {
                getPlaylists(event.id)
            }
        }
    }

    private fun getAlbum(id: String) {
        viewModelScope.launch {
            val album = ritmoUseCase.getAlbum(id = id)
            _state.value = state.value.copy(album = album)
        }
    }

    private fun getPlaylists(id: String) {
        val playlistsRef =
            db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
                .document(id)
        try {
            playlistsRef.addSnapshotListener { playlistsSnapshot, e ->
                if (playlistsSnapshot != null && playlistsSnapshot.exists()) {
                    val playlist = PlaylistUI(
                        id = playlistsSnapshot.id,
                        name = playlistsSnapshot.data?.get("name") as String,
                        tracks = playlistsSnapshot.data?.get("tracks") as MutableList<TrackUI>
                    )
                    _state.value = state.value.copy(playlist = playlist)
                } else {
                    Log.d("getPlaylists", e?.message.toString())
                }


            }
        } catch (e: Exception) {
            // Hata yönetimi
            Log.i("getPlaylists", e.message.toString())
        }
    }
}