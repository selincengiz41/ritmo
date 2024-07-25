package com.selincengiz.ritmo.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.presentation.Dimens
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding
import com.selincengiz.ritmo.presentation.Dimens.MaxText

@Composable
fun AlbumCard(modifier: Modifier = Modifier, albumUI: TrackAlbumUI, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(modifier = modifier.clickable { onClick() }) {

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .size(Dimens.CardSize),
            model = ImageRequest.Builder(context).data(albumUI.coverMedium).build(),
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier.height(ExtraSmallPadding))

        Text(
            text = albumUI.title ?: "",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .widthIn(max = MaxText),
            textAlign = TextAlign.Start,
            color = colorResource(
                id = R.color.text_title
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


    }
}

@Preview
@Composable
private fun AlbumPrev() {
    AlbumCard(
        albumUI = TrackAlbumUI(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "Somethig",
            null,
            null,
        ),
        onClick = {}
    )
}