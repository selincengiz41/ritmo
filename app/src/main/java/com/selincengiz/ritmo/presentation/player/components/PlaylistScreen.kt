package com.selincengiz.ritmo.presentation.player.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.Ritmo
import com.selincengiz.ritmo.presentation.player.PlayerState

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    state: PlayerState,
) {

    val currentIndex = state.trackList?.indexOf(state.track) ?: 0
    val lastIndex = state.trackList?.lastIndex ?: 0
    val list = state.trackList?.subList(currentIndex, lastIndex + 1)

    state.trackList?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(start = ExtraSmallPadding2),
                    text = "Next Songs",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )
            }
            items(count = list?.size ?: 0) { index2 ->
                list?.get(index2)?.let { track ->
                    Ritmo(
                        trackUI = track,
                        onClick = { }
                    )
                }
            }
        }
    }
}
