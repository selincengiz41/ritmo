package com.selincengiz.ritmo.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding2
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding
import com.selincengiz.ritmo.presentation.common.GlowingCard
import com.selincengiz.ritmo.presentation.common.Playlist
import com.selincengiz.ritmo.presentation.profile.components.PlaylistDialog
import com.selincengiz.ritmo.presentation.profile.components.ProfileImagePicker
import com.selincengiz.ritmo.ui.theme.BlueButtonColor
import com.selincengiz.ritmo.ui.theme.PurpleButtonColor

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    event: (ProfileEvent) -> Unit,
    state: ProfileState,
    navigateToLogin: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    //TODO: Side Effect
    /* LaunchedEffect(state.sideEffect) {
         state.sideEffect?.let {
             snackbarHostState.showSnackbar(
                 state.sideEffect,
                 duration = SnackbarDuration.Short,
                 withDismissAction = true
             )

         }
     }*/


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)

    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImagePicker(event = event, state = state)
            Spacer(modifier = Modifier.height(SmallPadding))
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
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(count = it.size) { item ->
                        it[item]?.let { playlist ->
                            Playlist(
                                playlistUI = playlist,
                                onClick = { })
                        }
                    }
                }
            }

        }
        GlowingCard(
            onClick = {
                event(ProfileEvent.Logout)
                navigateToLogin()
            },
            glowingColor = BlueButtonColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(42.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(BlueButtonColor, PurpleButtonColor)
                    ),
                    shape = RoundedCornerShape(25.dp)
                ),
            cornersRadius = Int.MAX_VALUE.dp
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.logout),
                color = colorResource(id = R.color.white)
            )
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