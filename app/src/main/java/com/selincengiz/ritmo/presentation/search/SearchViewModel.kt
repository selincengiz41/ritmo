package com.selincengiz.ritmo.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.ritmo.domain.usecase.ritmo.RitmoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val ritmoUseCase: RitmoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.SearchRitmo -> {
                searchRitmo()
            }

            is SearchEvent.Filter -> {
                _state.value = state.value.copy(selected = event.selected)
            }
        }
    }

    private fun searchRitmo() {
        viewModelScope.launch {
            val ritmo = ritmoUseCase.search(
                q = when (state.value.selected) {
                    0 -> state.value.searchQuery
                    1 -> "track:\"${state.value.searchQuery}\""
                    2 -> "album:\"${state.value.searchQuery}\""
                    3 -> "artist:\"${state.value.searchQuery}\""
                    else -> ""
                }
            )
            _state.value = state.value.copy(
                searchHistory = state.value.searchHistory.apply {
                    add(state.value.searchQuery)
                }.take(15).distinct().toMutableList()
            )
            _state.value = state.value.copy(ritmo = ritmo)
        }
    }
}