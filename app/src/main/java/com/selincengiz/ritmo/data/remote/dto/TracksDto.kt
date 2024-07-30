package com.selincengiz.ritmo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TracksDto(
    @SerializedName("data")
    val `data`: List<TrackDto>?
)