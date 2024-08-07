package com.selincengiz.ritmo.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.Ritmo

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    state: FavoriteState,
    navigateToPlayer: (TrackUI) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier.padding(start = MediumPadding1),
            text = "Favorite List",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 24.sp,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
        state.favoriteRitmo?.let {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2)
            ) {
                items(count = state.favoriteRitmo.size ) {
                    state.favoriteRitmo[it]?.let { track ->
                        Ritmo(
                            trackUI = track,
                            onClick = { navigateToPlayer(track) })
                    }
                }
            }
        }
    }
}