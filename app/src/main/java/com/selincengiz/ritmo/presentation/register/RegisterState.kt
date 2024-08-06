package com.selincengiz.ritmo.presentation.register


sealed interface RegisterState {
    data object Entry : RegisterState
    data object Success : RegisterState
    data class Error(val throwable: String) : RegisterState
}