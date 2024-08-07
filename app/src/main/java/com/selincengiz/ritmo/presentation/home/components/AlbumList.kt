package com.selincengiz.ritmo.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1

@Composable
fun AlbumList(
    modifier: Modifier = Modifier,
    albums: LazyPagingItems<TrackAlbumUI>,
    navigateToDetail: (String) -> Unit = ({})
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(count = albums.itemCount) {
            albums[it]?.let { album ->
                AlbumCard(
                    albumUI = album,
                    onClick = { navigateToDetail(album.id ?: "") })
            }
        }
    }
}