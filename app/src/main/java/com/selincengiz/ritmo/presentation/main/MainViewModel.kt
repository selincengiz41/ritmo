package com.selincengiz.ritmo.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.ritmo.domain.usecase.app_entry.AppEntryUseCase
import com.selincengiz.ritmo.presentation.login.LoginEvent
import com.selincengiz.ritmo.presentation.login.LoginState
import com.selincengiz.ritmo.presentation.main.components.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCase: AppEntryUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCase.readAppEntry().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                auth.currentUser?.let {
                    Route.RitmoNavigation.route
                } ?: Route.LoginNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Auth -> {
                startDestination = auth.currentUser?.let {
                    Route.RitmoNavigation.route
                } ?: Route.LoginNavigation.route
            }
        }
    }
}