package com.selincengiz.ritmo.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1

@Composable
fun ListRitmo(
    modifier: Modifier = Modifier,
    trackUI: LazyPagingItems<TrackUI>,
    navigateToPlayer: (List<TrackUI?>, Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(count = trackUI.itemCount) { index ->
            trackUI[index]?.let { track ->
                Ritmo(
                    trackUI = track,
                    onClick = { navigateToPlayer(trackUI.itemSnapshotList.items, index) })
            }
        }
    }
}