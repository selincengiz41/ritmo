package com.selincengiz.ritmo.presentation.player.components

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.MediaDownloadService
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.common.Playlist
import com.selincengiz.ritmo.presentation.player.PlayerEvent
import com.selincengiz.ritmo.presentation.player.PlayerState

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    state: PlayerState,
    event: (PlayerEvent) -> Unit
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier.clickable {
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, state.track?.link)
                        it.type = "text/plain"
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(35.dp))
            Icon(
                modifier = Modifier.clickable {
                    if (state.isDownloaded != true) {
                        val mediaUri = Uri.parse(state.track?.preview ?: "")
                        MediaDownloadService.startDownload(context, mediaUri) {
                            event(PlayerEvent.Download(state.track?.id ?: ""))
                        }
                    }
                },
                painter = if (state.isDownloaded == true) painterResource(id = R.drawable.ic_downloaded) else painterResource(
                    id = R.drawable.ic_download
                ),
                contentDescription = "Download",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(35.dp))
            Icon(
                modifier = Modifier.clickable {
                    event(PlayerEvent.GetPlaylists)
                    showBottomSheet = !showBottomSheet
                },
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(72.dp))

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(345.dp),
            model = ImageRequest.Builder(context).data(state.track?.album?.coverMedium).build(),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1),
            textAlign = TextAlign.Center,
            text = state.track?.title ?: "",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = Bold),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.track?.artist?.name ?: "",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Icon(
            modifier = Modifier
                .clickable { event(PlayerEvent.InsertDeleteTrack) }
                .align(Alignment.End)
                .padding(end = 20.dp),
            imageVector = if (state.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.White
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
            ) {
                state.playlists.let {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                        contentPadding = PaddingValues(all = ExtraSmallPadding2)
                    ) {
                        items(count = it.size) { item ->
                            it[item]?.let { playlist ->
                                Playlist(
                                    playlistUI = playlist,
                                    onClick = {
                                        event(PlayerEvent.AddToPlaylist(playlist.id))
                                        showBottomSheet = false
                                    },
                                    onDelete = {
                                        event(PlayerEvent.DeletePlaylist(playlist.id))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}