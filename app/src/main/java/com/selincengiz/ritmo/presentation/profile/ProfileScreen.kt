package com.selincengiz.ritmo.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.common.GlowingCard
import com.selincengiz.ritmo.ui.theme.BlueButtonColor
import com.selincengiz.ritmo.ui.theme.PurpleButtonColor

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, event: (ProfileEvent) -> Unit, navigateToLogin: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        GlowingCard(
            onClick = {
                event(ProfileEvent.Logout)
                navigateToLogin()
            },
            glowingColor = BlueButtonColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
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
    }
}