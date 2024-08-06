package com.selincengiz.ritmo.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = mutableStateOf<RegisterState>(RegisterState.Entry)
    val state: State<RegisterState> = _state

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> {
                if (!event.email.isNullOrBlank() && !event.password.isNullOrBlank() && !event.name.isNullOrBlank()) {
                    auth.createUserWithEmailAndPassword(event.email, event.password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = event.name
                                }
                                auth.currentUser!!.updateProfile(profileUpdates)
                                _state.value = RegisterState.Success
                            } else {
                                _state.value = RegisterState.Error(it.exception?.message.toString())
                            }
                        }
                } else {
                    _state.value = RegisterState.Error("Fill the blanks")
                }
            }
        }
    }
}