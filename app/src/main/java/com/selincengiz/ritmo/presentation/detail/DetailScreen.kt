package com.selincengiz.ritmo.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.EmptyScreen
import com.selincengiz.ritmo.presentation.common.Ritmo
import com.selincengiz.ritmo.util.ConnectivityHelper

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    navigateToPlayer: (List<TrackUI?>, Int) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(state.album?.coverBig).build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f),
                                Color.Black
                            ),
                            startY = size.height / 2,
                            endY = size.height
                        ),
                        size = size
                    )
                },
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1),
            text = state.album?.title ?: state.playlist?.name ?: "",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = Bold),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(ExtraSmallPadding))

        if (ConnectivityHelper.isOnline(context)) {
            state.album?.let {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = state.album.tracks?.size ?: 0) { index ->
                        state.album.tracks?.get(index)?.let { track ->
                            Ritmo(
                                trackUI = track,
                                onClick = { navigateToPlayer(state.album.tracks, index) })
                        }
                    }
                }
            }
            state.playlist?.let {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = state.playlist.tracks?.size ?: 0) { index ->
                        state.playlist.tracks?.get(index)?.let { track ->
                            Ritmo(
                                trackUI = track,
                                onClick = { navigateToPlayer(state.playlist.tracks, index) })
                        }
                    }
                }
            }
        } else {
            EmptyScreen()
        }
    }
}

