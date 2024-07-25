package com.selincengiz.ritmo.presentation.profile


sealed class ProfileEvent {
    data object Logout : ProfileEvent()
}