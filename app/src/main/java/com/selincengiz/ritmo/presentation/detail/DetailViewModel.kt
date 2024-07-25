package com.selincengiz.ritmo.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.GetAlbum -> {
                getAlbum(event.id)
            }
        }
    }

    private fun getAlbum(id: String) {
        viewModelScope.launch {
            val album = ritmoUseCase.getAlbum(id = id)
            _state.value = state.value.copy(album = album)
        }
    }
}