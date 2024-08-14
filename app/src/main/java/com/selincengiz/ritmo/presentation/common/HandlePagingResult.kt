package com.selincengiz.ritmo.presentation.common

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun handlePagingResult(
    title: String = "",
    items: LazyPagingItems<out Any>
): Boolean {
    val loadState = items.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            when (title) {
                "release" -> ReleaseShimmerEffect()
                "search" -> TrackShimmerEffect()
                "artist" -> ArtistCardShimmerEffect()
                "album" -> AlbumCardShimmerEffect()
                else -> {}
            }
            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        items.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}
