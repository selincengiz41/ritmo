package com.selincengiz.ritmo.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.selincengiz.ritmo.R
import com.selincengiz.ritmo.presentation.detail.DetailScreen
import com.selincengiz.ritmo.presentation.detail.DetailViewModel
import com.selincengiz.ritmo.presentation.detail.DetailsEvent
import com.selincengiz.ritmo.presentation.favorite.FavoriteScreen
import com.selincengiz.ritmo.presentation.home.HomeScreen
import com.selincengiz.ritmo.presentation.home.HomeViewModel
import com.selincengiz.ritmo.presentation.main.components.Route
import com.selincengiz.ritmo.presentation.navigator.components.BottomNavigation
import com.selincengiz.ritmo.presentation.navigator.components.BottomNavigationItem
import com.selincengiz.ritmo.presentation.profile.ProfileScreen
import com.selincengiz.ritmo.presentation.profile.ProfileViewModel
import com.selincengiz.ritmo.presentation.search.SearchScreen
import com.selincengiz.ritmo.presentation.search.SearchViewModel


@Composable
fun RitmoNavigator(navigateToLogin: () -> Unit) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, "Search"),
            BottomNavigationItem(icon = R.drawable.ic_favorite, "Favorite"),
            BottomNavigationItem(icon = R.drawable.ic_profile, "Profile")
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.FavoriteScreen.route -> 2
            Route.ProfileScreen.route -> 3
            else -> 0

        }
    }
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.FavoriteScreen.route ||
                backstackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {

                BottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTap(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTap(
                                navController = navController,
                                route = Route.FavoriteScreen.route
                            )

                            3 -> navigateToTap(
                                navController = navController,
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
                )
            }

        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {

            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    state = viewModel.state.value,
                    navigateToDetail = { id ->
                        navigateToDetail(
                            navController = navController,
                            id = id
                        )
                    }
                )
            }


            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent
                )
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<String?>("id")
                    ?.let { id ->
                        viewModel.onEvent(DetailsEvent.GetAlbum(id))
                        DetailScreen(
                            state = viewModel.state.value,
                            event = viewModel::onEvent,
                        )
                    }
            }

            composable(route = Route.FavoriteScreen.route) {
                //  val viewModel: BookmarkViewModel = hiltViewModel()
                //  val state = viewModel.state.value
                FavoriteScreen(
                    /* state = state, navigateToDetail = {
                     navigateToDetail(navController = navController, article = it)
                 }*/
                )
            }

            composable(route = Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()

                ProfileScreen(
                    event = viewModel::onEvent,
                    navigateToLogin = navigateToLogin
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

private fun navigateToDetail(navController: NavController, id: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
    navController.navigate(route = Route.DetailScreen.route) {
    }
}