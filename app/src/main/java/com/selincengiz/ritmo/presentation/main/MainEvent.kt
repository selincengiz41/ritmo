package com.selincengiz.ritmo.presentation.main

sealed class MainEvent {
    data object Auth : MainEvent()
}