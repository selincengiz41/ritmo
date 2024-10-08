package com.selincengiz.ritmo.data.remote

import com.selincengiz.ritmo.data.remote.dto.AlbumResponse
import com.selincengiz.ritmo.data.remote.dto.SearchResponse
import com.selincengiz.ritmo.data.remote.dto.TrackDto
import com.selincengiz.ritmo.util.Constants.ALBUM
import com.selincengiz.ritmo.util.Constants.ARTIST
import com.selincengiz.ritmo.util.Constants.SEARCH
import com.selincengiz.ritmo.util.Constants.TRACK
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RitmoApi {

    @GET(SEARCH)
    suspend fun search(
        @Query("q") q: String,
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): SearchResponse

    @GET(ALBUM)
    suspend fun getAlbum(
        @Path("id") id: String,
    ): AlbumResponse

    @GET(TRACK)
    suspend fun getTrack(
        @Path("id") id: String,
    ): TrackDto

    @GET(ARTIST)
    suspend fun getArtist(
        @Path("id") id: String,
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): SearchResponse
}