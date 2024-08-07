package com.selincengiz.ritmo.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding
import com.selincengiz.ritmo.ui.theme.DarkGray

@Composable
fun Release(
    modifier: Modifier = Modifier,
    trackUI: LazyPagingItems<TrackUI>,
    isFavorite: Boolean = false,
    onPlayClick: (id: String) -> Unit
) {
    val context = LocalContext.current
    val track = trackUI.itemSnapshotList.items.firstOrNull()
    Row(
        modifier
            .clickable { onPlayClick(track?.id ?: "") }
            .clip(RoundedCornerShape(6.dp))
            .background(DarkGray.copy(alpha = 0.6f))
    ) {

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .size(height = 144.dp, width = 136.dp),
            model = ImageRequest.Builder(context).data(track?.album?.coverMedium).build(),
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(SmallPadding)) {

            Text(
                text = track?.title ?: "",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = track?.artist?.name ?: "",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = Color.White
            )
            Text(
                text = track?.album?.title ?: "",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.White
                )

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
            }
        }
    }
}
