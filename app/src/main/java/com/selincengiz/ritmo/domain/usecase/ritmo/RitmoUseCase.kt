package com.selincengiz.ritmo.domain.usecase.ritmo


data class RitmoUseCase(
    val getAlbum: GetAlbum,
    val getTrack: GetTrack,
    val search: Search
)
