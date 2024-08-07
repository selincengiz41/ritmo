package com.selincengiz.ritmo.presentation.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.usecase.ritmo_local.RitmoLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    ritmoLocalUseCase: RitmoLocalUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteState())
    val state: State<FavoriteState> = _state

    init {
            ritmoLocalUseCase.getLocalTracks().onEach { tracks ->
                _state.value = state.value.copy(favoriteRitmo = tracks)
            }.launchIn(viewModelScope)
    }
}