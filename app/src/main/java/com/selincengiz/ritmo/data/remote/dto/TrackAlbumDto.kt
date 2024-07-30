package com.selincengiz.ritmo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TrackAlbumDto(
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("cover_big")
    val coverBig: String?,
    @SerializedName("cover_medium")
    val coverMedium: String?,
    @SerializedName("cover_small")
    val coverSmall: String?,
    @SerializedName("cover_xl")
    val coverXl: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("md5_image")
    val md5Image: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("tracklist")
    val tracklist: String?,
    @SerializedName("type")
    val type: String?
)