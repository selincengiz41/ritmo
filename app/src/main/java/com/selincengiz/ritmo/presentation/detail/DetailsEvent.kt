package com.selincengiz.ritmo.presentation.detail

sealed class DetailsEvent {
    data class GetAlbum(val id: String) : DetailsEvent()
}