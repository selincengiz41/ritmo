package com.selincengiz.ritmo.presentation.register


sealed class RegisterEvent{
    data class Register(val name: String?, val email: String?, val password: String?) : RegisterEvent()
}