package com.selincengiz.ritmo.presentation.home

sealed class HomeEvent {
    data class IsFavorite(val id: String) : HomeEvent()
}
