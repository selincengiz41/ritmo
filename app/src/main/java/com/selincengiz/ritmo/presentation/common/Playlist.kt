package com.selincengiz.ritmo.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding

@Composable
fun Playlist(modifier: Modifier = Modifier, playlistUI: PlaylistUI, onClick: () -> Unit) {

    Row(
        modifier
            .fillMaxWidth()
            .clickable { onClick() }) {

        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .size(height = 82.dp, width = 84.dp),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(SmallPadding)) {

            Text(
                text = playlistUI.name,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = Color.White
            )
        }
    }
}