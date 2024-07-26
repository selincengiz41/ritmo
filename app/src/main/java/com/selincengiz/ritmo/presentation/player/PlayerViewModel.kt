package com.selincengiz.ritmo.presentation.player

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.presentation.detail.DetailState
import com.selincengiz.ritmo.presentation.detail.DetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PlayerState())
    val state: State<PlayerState> = _state

    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.GetTrack -> {
                getTrack(event.id)
            }
        }
    }

    private fun getTrack(id: String) {
        viewModelScope.launch {
            val track = ritmoUseCase.getTrack(id = id)
            _state.value = state.value.copy(track = track)
        }
    }
}