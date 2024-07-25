package com.selincengiz.ritmo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("data")
    val `data`: List<Track>?
)