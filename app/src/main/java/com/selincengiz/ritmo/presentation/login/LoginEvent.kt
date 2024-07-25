package com.selincengiz.ritmo.presentation.login

sealed class LoginEvent {
    data class Login(val name: String?, val password: String?) : LoginEvent()
}