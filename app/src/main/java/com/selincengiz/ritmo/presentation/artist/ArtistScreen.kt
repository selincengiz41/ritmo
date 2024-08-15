package com.selincengiz.ritmo.presentation.artist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.ListRitmo
import com.selincengiz.ritmo.presentation.common.handlePagingResult

@Composable
fun ArtistScreen(
    modifier: Modifier = Modifier,
    state: ArtistState,
    navigateToPlayer: (List<TrackUI?>, Int) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(state.artist?.pictureMedium).build(),
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
            text = state.artist?.name ?: "",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = Bold),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(ExtraSmallPadding))

        state.artistTracks?.let {
            val handlePagingResult =
                handlePagingResult(
                    items = it.collectAsLazyPagingItems(),
                    title = "artistTrack"
                )
            if (handlePagingResult) {
                ListRitmo(
                    trackUI = it.collectAsLazyPagingItems(),
                    navigateToPlayer = { list, index -> navigateToPlayer(list, index) })
            }
        }
    }
}