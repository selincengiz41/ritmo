package com.selincengiz.ritmo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val `data`: List<Track?>?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("total")
    val total: Int?
)