package com.selincengiz.ritmo.presentation.player

import androidx.annotation.OptIn
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.usecase.ritmo_download.RitmoDownloadUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_firebase.RitmoFirebaseUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(UnstableApi::class)
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val ritmoLocalUseCase: RitmoLocalUseCase,
    private val ritmoFirebaseUseCase: RitmoFirebaseUseCase,
    private val ritmoDownloadUseCase: RitmoDownloadUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PlayerState())
    val state: State<PlayerState> = _state

    fun onEvent(event: PlayerEvent) {
        when (event) {
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

            is PlayerEvent.UpdateTrack -> {
                updateTrack(event.track, event.index)
            }

            is PlayerEvent.Download -> {
                viewModelScope.launch {
                    ritmoDownloadUseCase.insertDownloaded(event.id)
                    _state.value = state.value.copy(isDownloaded = true)
                }
            }

            is PlayerEvent.ChangeSong -> {
                changeSong(event.index)
            }
        }
    }

    private fun changeSong(index: Int) {
        updateTrack(_state.value.trackList, index)
    }

    private fun updateTrack(trackList: List<TrackUI?>?, index: Int?) {
        viewModelScope.launch {
            val track = trackList?.get(index ?: 0)
            _state.value = state.value.copy(track = track, trackList = trackList)
            ritmoDownloadUseCase.getDownloaded().let {
                if (it.contains(track?.id)) {
                    _state.value = state.value.copy(isDownloaded = true)
                } else {
                    _state.value = state.value.copy(isDownloaded = false)
                }
            }
            val trackResponse =
                ritmoLocalUseCase.getLocalTrack(id = track?.id ?: "")
            _state.value = if (trackResponse == null) {
                state.value.copy(isFavorite = false)
            } else {
                state.value.copy(isFavorite = true)
            }
        }
    }

    private fun insertDeleteTrack(track: TrackUI) {
        viewModelScope.launch {
            val trackResponse = ritmoLocalUseCase.getLocalTrack(id = track.id ?: "")
            if (trackResponse == null) {
                ritmoLocalUseCase.insertTrack(track)
                _state.value = state.value.copy(isFavorite = true)
            } else {
                ritmoLocalUseCase.deleteTrack(track)
                _state.value = state.value.copy(isFavorite = false)
            }
        }
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