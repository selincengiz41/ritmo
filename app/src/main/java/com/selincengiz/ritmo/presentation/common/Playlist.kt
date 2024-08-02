package com.selincengiz.ritmo.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding

@Composable
fun Playlist(
    modifier: Modifier = Modifier,
    playlistUI: PlaylistUI,
    onClick: () -> Unit,
    onDelete: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
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

        Column(modifier = Modifier.padding(SmallPadding).fillMaxWidth(0.8f)) {

            Text(
                text = playlistUI.name,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "settings",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { expanded = true },
                tint = Color.White
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onDelete(playlistUI.id)
                    },
                    text = {
                        Text(text = "Delete")
                    }
                )
            }
        }
    }
}