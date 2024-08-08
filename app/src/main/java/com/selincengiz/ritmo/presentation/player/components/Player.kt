package com.selincengiz.ritmo.presentation.player.components

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.smoothstreaming.SsMediaSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.presentation.player.PlayerState
import com.selincengiz.ritmo.util.ConnectivityHelper
import com.selincengiz.ritmo.util.DownloadUtil.cacheDataSourceFactory

@Composable
@androidx.annotation.OptIn(UnstableApi::class)
fun Player(
    modifier: Modifier = Modifier,
    state: PlayerState,
    event: (Int) -> Unit
) {
    val context = LocalContext.current
    var player: Player? by remember {
        mutableStateOf(null)
    }

    val playerView = createPlayerView(player)
    playerView.controllerAutoShow = true
    playerView.keepScreenOn = true
    playerView.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)


    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                player = initPlayer(context, state).also {
                    it.addListener(object : Player.Listener {
                        override fun onEvents(
                            player: Player,
                            events: Player.Events
                        ) {
                            if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)) {
                                val currentItemIndex = player.currentMediaItemIndex
                                event(currentItemIndex)
                            }
                        }
                    })
                }
                playerView.onResume()
            }

            Lifecycle.Event.ON_PAUSE -> {
                playerView.apply {
                    player?.release()
                    onPause()
                    player = null
                }
            }

            else -> {}
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = modifier
    )
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun initPlayer(context: Context, state: PlayerState): Player {
    return ExoPlayer.Builder(context).build().apply {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        state.trackList?.forEach { track ->
            val uri = Uri.parse(track?.preview)
            val mediaSource = buildMediaSource(uri, defaultHttpDataSourceFactory, context)
            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        setMediaSource(concatenatingMediaSource)
        seekTo(state.trackList?.indexOf(state.track) ?: 0, 0)
        playWhenReady = true
        prepare()
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun buildMediaSource(
    uri: Uri,
    defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory,
    context: Context
): MediaSource {
    val type = Util.inferContentType(uri)

    return when (type) {
        C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_SS -> SsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_OTHER -> {
            val isOnline = ConnectivityHelper.isOnline(context = context)
            ProgressiveMediaSource.Factory(if (isOnline) defaultHttpDataSourceFactory else cacheDataSourceFactory!!)
                .createMediaSource(MediaItem.fromUri(uri))
        }

        else -> {
            throw IllegalStateException("Unsupported type: $type")
        }
    }
}

@Composable
fun createPlayerView(player: Player?): PlayerView {
    val context = LocalContext.current
    val playerView = remember {
        PlayerView(context).apply {
            this.player = player
        }
    }
    DisposableEffect(key1 = player) {
        playerView.player = player
        onDispose {
            playerView.player = null
        }
    }
    return playerView

}