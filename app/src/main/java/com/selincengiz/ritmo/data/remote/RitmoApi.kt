package com.selincengiz.ritmo.data.remote

import com.selincengiz.ritmo.data.remote.dto.AlbumResponse
import com.selincengiz.ritmo.data.remote.dto.SearchResponse
import com.selincengiz.ritmo.data.remote.dto.Track
import com.selincengiz.ritmo.util.Constants.ALBUM
import com.selincengiz.ritmo.util.Constants.SEARCH
import com.selincengiz.ritmo.util.Constants.TRACK
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RitmoApi {

    @GET(SEARCH)
    suspend fun search(
        @Query("q") q: String,
    ): SearchResponse

    @GET(ALBUM)
    suspend fun getAlbum(
        @Path("id") id: String,
    ): AlbumResponse

    @GET(TRACK)
    suspend fun getTrack(
        @Path("id") id: Int,
    ): Track
}