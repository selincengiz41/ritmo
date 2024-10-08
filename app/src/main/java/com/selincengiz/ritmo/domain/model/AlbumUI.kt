package com.selincengiz.ritmo.domain.model


data class AlbumUI(
    val artist: ArtistUI?,
    val available: Boolean?,
    val cover: String?,
    val coverBig: String?,
    val coverMedium: String?,
    val coverSmall: String?,
    val coverXl: String?,
    val duration: Int?,
    val explicitContentCover: Int?,
    val explicitContentLyrics: Int?,
    val explicitLyrics: Boolean?,
    val fans: Int?,
    val genreId: Int?,
    val id: String?,
    val label: String?,
    val link: String?,
    val md5Image: String?,
    val nbTracks: Int?,
    val recordType: String?,
    val releaseDate: String?,
    val share: String?,
    val title: String?,
    val tracklist: String?,
    val tracks: List<TrackUI>?,
    val type: String?,
    val upc: String?
)