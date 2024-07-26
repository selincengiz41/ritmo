package com.selincengiz.ritmo.presentation.player

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    state: PlayerState,
    event: (PlayerEvent) -> Unit,
) {
    val context = LocalContext.current


    val exoPlayer = ExoPlayer.Builder(context).build()
    exoPlayer.addListener(object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            // Hide video title after playing for 200 milliseconds
            //if (player.contentPosition >= 200) visible.value = false
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
         /*   // Callback when the video changes
            onVideoChange(this@apply.currentPeriodIndex)
            visible.value = true
            videoTitle.value = mediaItem?.mediaMetadata?.displayTitle.toString()*/
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            // Callback when the video playback state changes to STATE_ENDED
         /*   if (playbackState == ExoPlayer.STATE_ENDED) {
                isVideoEnded.invoke(true)
            }*/
        }
    })
    // Set MediaSource to ExoPlayer
    LaunchedEffect(state.track?.preview) {
        val mediaItem = MediaItem.Builder()
            .setUri(state.track?.preview ?: "")
            .setMediaMetadata(MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC).build())
            .build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.pauseAtEndOfMediaItems = true
    }
    exoPlayer.prepare()
    exoPlayer.play()

    LocalLifecycleOwner.current.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_START -> {
                    // Start playing when the Composable is in the foreground
                    if (exoPlayer.isPlaying.not()) {
                        exoPlayer.play()
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    // Pause the player when the Composable is in the background
                    exoPlayer.pause()
                }

                else -> {
                    // Nothing
                }
            }
        }
    })

    // Manage lifecycle events
    DisposableEffect(state.track?.preview) {
        onDispose {
            exoPlayer.release()
        }
    }

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
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = "Download",
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

        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set your desired height
        )

    }
}