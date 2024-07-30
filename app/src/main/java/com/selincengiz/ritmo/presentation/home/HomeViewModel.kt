package com.selincengiz.ritmo.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase,
    private val ritmoLocalUseCase: RitmoLocalUseCase
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        viewModelScope.launch {
            val discover = ritmoUseCase.search("a")
            _state.value = state.value.copy(
                discover = discover?.map { it?.album },
                track = discover?.get(7),
                artist = listOf(discover?.get(7)?.artist, discover?.get(6)?.artist)
            )
            val trackResponse = ritmoLocalUseCase.getLocalTrack(id = discover?.get(7)?.id ?: "")
            _state.value = if (trackResponse == null) {
                state.value.copy(
                    isFavorite = false
                )
            } else {
                state.value.copy(
                    isFavorite = true
                )
            }
        }
    }
}