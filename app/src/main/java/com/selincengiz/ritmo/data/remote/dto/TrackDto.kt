package com.selincengiz.ritmo.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("album")
    val trackAlbumDto: TrackAlbumDto?,
    @SerializedName("artist")
    val artistDto: ArtistDto?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int?,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int?,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("md5_image")
    val md5Image: String?,
    @SerializedName("preview")
    val preview: String?,
    @SerializedName("rank")
    val rank: String?,
    @SerializedName("readable")
    val readable: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("title_short")
    val titleShort: String?,
    @SerializedName("title_version")
    val titleVersion: String?,
    @SerializedName("type")
    val type: String?
)