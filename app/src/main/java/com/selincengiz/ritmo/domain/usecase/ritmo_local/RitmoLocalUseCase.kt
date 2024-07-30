package com.selincengiz.ritmo.domain.usecase.ritmo_local

data class RitmoLocalUseCase (
    val deleteTrack: DeleteTrack,
    val getLocalTrack: GetLocalTrack,
    val getLocalTracks: GetLocalTracks,
    val insertTrack: InsertTrack
)