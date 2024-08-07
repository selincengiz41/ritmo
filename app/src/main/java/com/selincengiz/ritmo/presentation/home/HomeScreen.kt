package com.selincengiz.ritmo.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding
import com.selincengiz.ritmo.presentation.common.handlePagingResultAlbum
import com.selincengiz.ritmo.presentation.common.handlePagingResultArtist
import com.selincengiz.ritmo.presentation.common.handlePagingResultTrack
import com.selincengiz.ritmo.presentation.home.components.AlbumList
import com.selincengiz.ritmo.presentation.home.components.ArtistList
import com.selincengiz.ritmo.presentation.home.components.Release

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    event: (HomeEvent) -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToPlayer: (String) -> Unit
) {


    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = MediumPadding1)
                    .statusBarsPadding()
            ) {

                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.padding(start = MediumPadding1),
                    text = "Popular Albums",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(SmallPadding))

                state.discover?.let {
                    val handlePagingResult =
                        handlePagingResultAlbum(albums = it.collectAsLazyPagingItems())
                    if (handlePagingResult) {
                        AlbumList(
                            albums = it.collectAsLazyPagingItems(),
                            navigateToDetail = { item ->
                                navigateToDetail(item)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.padding(start = MediumPadding1),
                    text = "New Release",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(SmallPadding))
                state.track?.let {
                    val handlePagingResult =
                        handlePagingResultTrack(tracks = it.collectAsLazyPagingItems(), title = "release")
                    if (handlePagingResult) {
                        event(
                            HomeEvent.IsFavorite(
                                it.collectAsLazyPagingItems().itemSnapshotList.items.firstOrNull()?.id
                                    ?: ""
                            )
                        )
                        Release(
                            trackUI = it.collectAsLazyPagingItems(),
                            modifier = Modifier.padding(MediumPadding1),
                            onPlayClick = { id -> navigateToPlayer(id) },
                            isFavorite = state.isFavorite ?: false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.padding(start = MediumPadding1),
                    text = "Artists May You Like",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )
                state.artist?.let {
                    val handlePagingResult =
                        handlePagingResultArtist(artists = it.collectAsLazyPagingItems())
                    if (handlePagingResult) {
                        ArtistList(artist = it.collectAsLazyPagingItems())
                    }
                }
            }
        }
    }
}
