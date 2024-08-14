package com.selincengiz.ritmo.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding
import com.selincengiz.ritmo.presentation.common.Playlist
import com.selincengiz.ritmo.presentation.profile.components.PlaylistDialog
import com.selincengiz.ritmo.presentation.profile.components.ProfileImagePicker

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    event: (ProfileEvent) -> Unit,
    state: ProfileState,
    navigateToLogin: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .statusBarsPadding()

    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .clickable {
                        event(ProfileEvent.Logout)
                        navigateToLogin()
                    }
                    .align(Alignment.End)
                    .padding(end = 15.dp),
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "logout",
                tint = Color.White
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp)
            ) {
                ProfileImagePicker(event = event, state = state)
                Spacer(modifier = Modifier.width(MediumPadding1))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = state.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
            Canvas(modifier = Modifier.fillMaxWidth()) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(Float.MAX_VALUE, 0f),
                    strokeWidth = 2f
                )
            }
            Spacer(modifier = Modifier.height(MediumPadding1))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding1),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Playlists",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = Color.White,
                )

                Icon(
                    modifier = Modifier.clickable { showDialog = true },
                    imageVector = Icons.Default.Add,
                    contentDescription = "create playlist",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(SmallPadding))
            state.playlists.let {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = it.size) { item ->
                        it[item]?.let { playlist ->
                            Playlist(
                                playlistUI = playlist,
                                onClick = { navigateToDetail(playlist.id) },
                                onDelete = { event(ProfileEvent.DeletePlaylist(playlist.id)) }
                            )
                        }
                    }
                }
            }
        }

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            hostState = snackbarHostState
        )
        if (showDialog) {
            PlaylistDialog(onDismiss = { showDialog = false }, onConfirm = { playlistName ->
                event(ProfileEvent.CreatePlayList(playlistName))
                showDialog = false
            })
        }
    }
}