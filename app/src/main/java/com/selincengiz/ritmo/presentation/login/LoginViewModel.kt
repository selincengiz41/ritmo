package com.selincengiz.ritmo.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = mutableStateOf<LoginState>(LoginState.Entry)
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                if (!event.email.isNullOrBlank() && !event.password.isNullOrBlank()) {
                    auth.signInWithEmailAndPassword(event.email, event.password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                _state.value = LoginState.Success
                            } else {
                                _state.value = LoginState.Error(it.exception?.message.toString())
                            }
                        }
                } else {
                    _state.value = LoginState.Error("Fill the blanks")
                }
            }
        }
    }
}