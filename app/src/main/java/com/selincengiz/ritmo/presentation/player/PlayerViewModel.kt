package com.selincengiz.ritmo.presentation.player

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase,
    private val ritmoLocalUseCase: RitmoLocalUseCase,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _state = mutableStateOf(PlayerState())
    val state: State<PlayerState> = _state

    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.GetTrack -> {
                getTrack(event.id)
            }

            is PlayerEvent.InsertDeleteTrack -> {
                insertDeleteTrack(_state.value.track!!)
            }

            is PlayerEvent.GetPlaylists -> {
                getPlaylists()
            }

            is PlayerEvent.AddToPlaylist -> {
                addToPlaylist(event.playlistId)
            }
        }
    }

    private fun getTrack(id: String) {
        viewModelScope.launch {
            val track = ritmoUseCase.getTrack(id = id)
            val trackResponse = ritmoLocalUseCase.getLocalTrack(id = track.id ?: "")
            _state.value = if (trackResponse == null) {
                state.value.copy(track = track, isFavorite = false)
            } else {
                state.value.copy(track = track, isFavorite = true)
            }
        }
    }

    private fun insertDeleteTrack(track: TrackUI) {
        viewModelScope.launch {
            val trackResponse = ritmoLocalUseCase.getLocalTrack(id = track.id ?: "")
            if (trackResponse == null) {
                insertTrack(track)
            } else {
                deleteTrack(track)
            }
        }
    }

    private suspend fun insertTrack(track: TrackUI) {
        ritmoLocalUseCase.insertTrack(track)
        _state.value = state.value.copy(sideEffect = "Track Saved")
    }

    private suspend fun deleteTrack(track: TrackUI) {
        ritmoLocalUseCase.deleteTrack(track)
        _state.value = state.value.copy(sideEffect = "Track Deleted")
    }

    private fun getPlaylists() {

        val playlistsRef =
            db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
        try {
            playlistsRef.addSnapshotListener { playlistsSnapshot, e ->
                val templist = mutableListOf<PlaylistUI?>()

                playlistsSnapshot?.forEach { doc ->
                    templist.add(
                        doc.toObject(PlaylistUI::class.java)
                  /*      PlaylistUI(
                            id = doc.id,
                            name = doc.get("name") as String,
                            tracks = doc.toObject(ListTrackUI::class.java).listTrack.toMutableList()
                        )*/
                    )
                }
                Log.i("getPlaylists", templist[0]?.tracks?.get(0)?.preview.toString())
                _state.value = state.value.copy(playlists = templist)
            }
        } catch (e: Exception) {
            // Hata yönetimi
            Log.i("getPlaylists", e.message.toString())
        }
    }

    private fun addToPlaylist(playlistId: String) {
        val playlistsRef =
            db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
                .document(playlistId)
        val newList= state.value.playlists.filter { it?.id == playlistId }.get(0)?.tracks
         newList?.add(state.value.track!!)

        playlistsRef.update(
            "tracks",
           newList
        )
            .addOnSuccessListener {
                // Başarılı
                println("Playlist successfully added!")
            }
            .addOnFailureListener { e ->
                // Hata
                println("Error adding playlist: $e")
            }
    }
}