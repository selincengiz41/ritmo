package com.selincengiz.ritmo.presentation.onboarding

import androidx.annotation.DrawableRes
import com.selincengiz.ritmo.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image:Int
)

val pages = listOf(
    Page(
        title = "Keşfet ve Dinle",
        description = "Dünyanın dört bir yanından en sevdiğiniz müzikleri keşfedin. ",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Offline Mod",
        description = " İnternet bağlantınız olmadığında bile müziğin tadını çıkarın. Sevdiğiniz şarkıları cihazınıza indirip dilediğiniz zaman, dilediğiniz yerde dinleyin.",
        image = R.drawable.onboarding2
    ),
)