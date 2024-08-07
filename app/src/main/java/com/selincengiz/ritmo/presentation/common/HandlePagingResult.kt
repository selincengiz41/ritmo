package com.selincengiz.ritmo.presentation.common

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI

@Composable
fun handlePagingResultTrack(
    title: String = "",
    tracks: LazyPagingItems<TrackUI>
): Boolean {
    val loadState = tracks.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            if (title == "search")
                TrackShimmerEffect()
            else
                ReleaseShimmerEffect()

            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        tracks.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun handlePagingResultArtist(
    artists: LazyPagingItems<ArtistUI>
): Boolean {
    val loadState = artists.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ArtistCardShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        artists.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun handlePagingResultAlbum(
    albums: LazyPagingItems<TrackAlbumUI>
): Boolean {
    val loadState = albums.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            AlbumCardShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        albums.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}