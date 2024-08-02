package com.selincengiz.ritmo.presentation.player.components

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
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
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING

@Composable
@androidx.annotation.OptIn(UnstableApi::class)
fun Player(modifier: Modifier = Modifier, passedString: String) {
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
                if (Build.VERSION.SDK_INT > 23) {
                    player = initPlayer(context, passedString)
                    playerView.onResume()
                }
            }

            Lifecycle.Event.ON_RESUME -> {
                if (Build.VERSION.SDK_INT <= 23) {
                    player = initPlayer(context, passedString)
                    playerView.onResume()
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                if (Build.VERSION.SDK_INT <= 23) {
                    playerView.apply {
                        player?.release()
                        onPause()
                        player = null
                    }
                }
            }

            Lifecycle.Event.ON_STOP -> {
                if (Build.VERSION.SDK_INT > 23) {
                    playerView.apply {
                        player?.release()
                        onPause()
                        player = null
                    }
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
        // 1. Create a LifecycleEventObserver to handle lifecycle events.
        val observer = LifecycleEventObserver { source, event ->
            // 2. Call the provided onEvent callback with the source and event.
            onEvent(source, event)
        }

        // 3. Add the observer to the lifecycle of the provided LifecycleOwner.
        lifecycleOwner.lifecycle.addObserver(observer)

        // 4. Remove the observer when the composable is disposed.
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun initPlayer(context: Context, passedString: String): Player {
    return ExoPlayer.Builder(context).build().apply {
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val uri =
            Uri.parse(passedString)
        val mediaSource = buildMediaSource(uri, defaultHttpDataSourceFactory)
        setMediaSource(mediaSource)
        playWhenReady = true
        prepare()
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun buildMediaSource(
    uri: Uri,
    defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory,
): MediaSource {
    val type = Util.inferContentType(uri)
    return when (type) {
        C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_SS -> SsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        C.CONTENT_TYPE_OTHER -> ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

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