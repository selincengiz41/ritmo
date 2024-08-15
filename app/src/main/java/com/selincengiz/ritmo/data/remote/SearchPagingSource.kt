package com.selincengiz.ritmo.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.domain.model.TrackUI

class SearchPagingSource(
    private val ritmoApi: RitmoApi,
    private val searchQuery: String = "",
    private val id: String = ""
) : PagingSource<Int, TrackUI>() {
    private var totalTracksCount = 0

    override fun getRefreshKey(state: PagingState<Int, TrackUI>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackUI> {
        val page = params.key ?: 1
        return try {
            val tracksResponse = if (searchQuery.isEmpty()) {
                ritmoApi.getArtist(id = id, index = page, limit = params.loadSize)
            } else {
                ritmoApi.search(q = searchQuery, index = page, limit = params.loadSize)
            }

            totalTracksCount += tracksResponse.data?.size ?: 0
            val tracks = tracksResponse.data!!.map { it!!.toTrackUI() }

            LoadResult.Page(
                data = tracks,
                nextKey = if (totalTracksCount == tracksResponse.total) null else page + 1,
                prevKey = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}