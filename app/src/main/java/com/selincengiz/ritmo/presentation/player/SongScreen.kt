package com.selincengiz.ritmo.presentation.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selincengiz.ritmo.presentation.player.components.Player
import com.selincengiz.ritmo.presentation.player.components.PlayerScreen
import com.selincengiz.ritmo.presentation.player.components.PlaylistScreen

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongScreen(
    modifier: Modifier = Modifier,
    state: PlayerState,
    event: (PlayerEvent) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            2
        }
        HorizontalPager(modifier = Modifier.fillMaxHeight(0.75f), state = pagerState) { index ->
            when (index) {
                0 -> PlayerScreen(
                    modifier = Modifier,
                    state = state,
                    event = event
                )
                1 -> PlaylistScreen(
                    modifier = Modifier,
                    state = state
                )
            }
        }

        state.track?.preview?.let {
            Player(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                state = state,
                event = { index -> event(PlayerEvent.ChangeSong(index)) }
            )
        }
    }
}