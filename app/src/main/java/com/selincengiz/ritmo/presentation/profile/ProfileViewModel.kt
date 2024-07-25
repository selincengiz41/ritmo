package com.selincengiz.ritmo.presentation.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Logout -> {
                auth.signOut()
            }
        }
    }
}