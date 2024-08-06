package com.selincengiz.ritmo.presentation.player

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.RitmoFirebaseUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase,
    private val ritmoLocalUseCase: RitmoLocalUseCase,
    private val ritmoFirebaseUseCase: RitmoFirebaseUseCase,
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

            is PlayerEvent.DeletePlaylist -> {
                ritmoFirebaseUseCase.deletePlaylist(event.id)
                getPlaylists()
            }
            is PlayerEvent.UpdateTrack ->{
                _state.value = state.value.copy(track = event.track)
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
        viewModelScope.launch {
            val playlists = ritmoFirebaseUseCase.getPlaylists()
            _state.value = state.value.copy(playlists = playlists)
        }
    }

    private fun addToPlaylist(playlistId: String) {
        viewModelScope.launch {
            ritmoFirebaseUseCase.addPlaylist(id = playlistId, trackUI = state.value.track!!)
        }
    }
}