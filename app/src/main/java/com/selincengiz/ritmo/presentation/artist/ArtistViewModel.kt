package com.selincengiz.ritmo.presentation.artist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ArtistState())
    val state: State<ArtistState> = _state

    fun onEvent(event: ArtistEvent) {
        when (event) {
            is ArtistEvent.GetArtist -> {
                getArtist(event.artist)
            }
        }
    }

    private fun getArtist(artist: ArtistUI) {
        viewModelScope.launch {
            val artistTracks = ritmoUseCase.getArtist(id = artist.id ?: "")
            _state.value = state.value.copy(artistTracks = artistTracks, artist = artist)
        }
    }
}