package com.selincengiz.ritmo.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()
    data object SearchRitmo : SearchEvent()
}