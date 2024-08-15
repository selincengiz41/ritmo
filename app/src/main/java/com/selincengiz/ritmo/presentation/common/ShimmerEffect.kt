package com.selincengiz.ritmo.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.Dimens
import com.selincengiz.ritmo.presentation.Dimens.ExtraSmallPadding
import com.selincengiz.ritmo.presentation.Dimens.MediumPadding1
import com.selincengiz.ritmo.presentation.Dimens.SmallPadding

fun Modifier.shimmerEffect(cornerRadius: CornerRadius = CornerRadius(x = 12f, y = 12f)) = composed {
    val transition = rememberInfiniteTransition(label = "shimmer effect")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "transparency of the background color"
    ).value
    val color = colorResource(id = R.color.shimmer).copy(alpha = alpha)
    drawBehind {
        drawRoundRect(
            color = color,
            cornerRadius = cornerRadius
        )
    }
}

@Composable
fun AlbumCardShimmerEffect(
) {
    Row(
        modifier = Modifier.padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding1)
    ) {
        repeat(10) {
            Column(
                modifier = Modifier
                    .padding(ExtraSmallPadding)
                    .width(Dimens.CardSize)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .size(Dimens.CardSize)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(ExtraSmallPadding))
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth(0.5f)
                        .height(10.dp)
                        .shimmerEffect(),

                    )
            }
        }
    }
}

@Composable
fun ArtistCardShimmerEffect() {
    Row(
        modifier = Modifier.padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding1)
    ) {
        repeat(10) {
            Column(
                modifier = Modifier
                    .padding(ExtraSmallPadding)
                    .width(Dimens.ArtistSize)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Dimens.ArtistSize)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(ExtraSmallPadding))
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.5f)
                        .shimmerEffect()
                        .height(10.dp)
                )
            }
        }
    }
}

@Composable
fun ReleaseShimmerEffect() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(6.dp))
            .padding(MediumPadding1)
            .shimmerEffect()
    ) {}
}

@Composable
fun TrackShimmerEffect() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1)
    ) {
        repeat(10) {
            Row(
                modifier = Modifier
                    .padding(ExtraSmallPadding)
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .size(height = 82.dp, width = 84.dp)
                        .shimmerEffect(),
                )
                Column(
                    modifier = Modifier
                        .padding(SmallPadding)
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(15.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(10.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(10.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}