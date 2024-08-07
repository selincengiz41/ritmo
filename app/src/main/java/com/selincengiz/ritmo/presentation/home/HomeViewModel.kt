package com.selincengiz.ritmo.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
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
            val trackFlow = ritmoUseCase.search("a")
            val discoverFlow = trackFlow.map { pagingData ->
                pagingData.map { it.album!! }
            }
            val artistFlow = trackFlow.map { pagingData ->
                pagingData.map { it.artist!! }
            }

            _state.value = HomeState(
                discover = discoverFlow,
                track = trackFlow,
                artist = artistFlow
            )
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.IsFavorite -> {
                viewModelScope.launch {
                    val trackResponse =
                        ritmoLocalUseCase.getLocalTrack(id = event.id)
                    _state.value = _state.value.copy(
                        isFavorite = trackResponse != null
                    )
                }
            }
        }
    }
}