package com.selincengiz.ritmo.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding
import com.selincengiz.ritmo.presentation.home.components.AlbumCard
import com.selincengiz.ritmo.presentation.home.components.ArtistCard
import com.selincengiz.ritmo.presentation.home.components.Release

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    navigateToDetail: (String) -> Unit,
    navigateToPlayer: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = MediumPadding1)
                    .statusBarsPadding()
            ) {
                Spacer(modifier = Modifier.height(ExtraSmallPadding2))

                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = MediumPadding1),
                        text = "Ritmo",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "notifications",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.padding(start = MediumPadding1),
                    text = "Popular Albums",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(SmallPadding))

                LazyRow(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = state.discover?.size ?: 0) {
                        state.discover?.get(it)?.let { album ->
                            AlbumCard(
                                albumUI = album,
                                onClick = { navigateToDetail(album.id ?: "") })
                        }
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

                Release(
                    modifier = Modifier.padding(MediumPadding1),
                    trackUI = state.track ?: return@Column,
                    isFavorite = state.isFavorite ?: false,
                    onPlayClick = { navigateToPlayer(state.track.id ?: "") },
                )

                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.padding(start = MediumPadding1),
                    text = "Artists May You Like",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )

                LazyRow(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = state.artist?.size ?: 0) {
                        state.artist?.get(it)?.let { artist ->
                            ArtistCard(artistUI = artist)
                        }
                    }
                }

            }
        }
    }

}

@Preview
@Composable
private fun HomePrev() {
    HomeScreen(state = HomeState(), navigateToPlayer = {}, navigateToDetail = {})

}