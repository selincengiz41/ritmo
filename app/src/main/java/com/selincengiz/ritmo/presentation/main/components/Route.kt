package com.selincengiz.ritmo.presentation.main.components

sealed class Route(
    val route: String
) {
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object RitmoNavigation : Route(route = "ritmoNavigation")
    data object RitmoNavigatorScreen : Route(route = "ritmoNavigatorScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object DetailScreen : Route(route = "detailScreen")
    data object ArtistScreen : Route(route = "artistScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object FavoriteScreen : Route(route = "favoriteScreen")
    data object LoginScreen : Route(route = "loginScreen")
    data object RegisterScreen : Route(route = "registerScreen")
    data object LoginNavigation : Route(route = "loginNavigation")
    data object ProfileScreen : Route(route = "profileScreen")
    data object SongScreen : Route(route = "songScreen")

}