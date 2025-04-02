package com.bravedroid.momentor.libraries.presentation.navigation

sealed class NavigationRoute(val route: String) {
    data object Home : NavigationRoute("home")
    data object Photos : NavigationRoute("photos")
    data object Profile : NavigationRoute("profile")

    companion object {
        val bottomRouteList = listOf(
            Home.route,
            Photos.route,
            Profile.route,
        )
    }
}
