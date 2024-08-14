package com.selincengiz.ritmo.presentation.main.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.selincengiz.ritmo.presentation.login.LoginScreen
import com.selincengiz.ritmo.presentation.login.LoginViewModel
import com.selincengiz.ritmo.presentation.main.MainEvent
import com.selincengiz.ritmo.presentation.navigator.RitmoNavigator
import com.selincengiz.ritmo.presentation.onboarding.OnBoardingScreen
import com.selincengiz.ritmo.presentation.onboarding.OnBoardingViewModel
import com.selincengiz.ritmo.presentation.register.RegisterScreen
import com.selincengiz.ritmo.presentation.register.RegisterViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavGraph(
    startDestination: String,
    mainEvent: (MainEvent) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }

        navigation(
            route = Route.RitmoNavigation.route,
            startDestination = Route.RitmoNavigatorScreen.route
        ) {
            composable(route = Route.RitmoNavigatorScreen.route) {
                RitmoNavigator(navigateToLogin = { mainEvent(MainEvent.Auth) })
            }
        }

        navigation(
            route = Route.LoginNavigation.route,
            startDestination = Route.LoginScreen.route
        ) {
            composable(
                route = Route.LoginScreen.route
            ) {
                val viewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    loginState = viewModel.state.value,
                    event = viewModel::onEvent,
                    navigateToHome = { mainEvent(MainEvent.Auth) },
                    navigateToRegister = {
                        navigateToTap(
                            navController = navController,
                            route = Route.RegisterScreen.route
                        )
                    }
                )
            }
            composable(
                route = Route.RegisterScreen.route
            ) {
                val viewModel: RegisterViewModel = hiltViewModel()
                RegisterScreen(
                    registerState = viewModel.state.value,
                    event = viewModel::onEvent,
                    navigateToHome = { mainEvent(MainEvent.Auth) },
                    navigateToLogin = {
                        navigateToTap(
                            navController = navController,
                            route = Route.LoginScreen.route
                        )
                    }
                )
            }
        }
    }
}

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}


