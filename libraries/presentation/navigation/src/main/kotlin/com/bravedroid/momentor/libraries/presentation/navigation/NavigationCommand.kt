package com.bravedroid.momentor.libraries.presentation.navigation
import androidx.navigation.NavOptions

sealed interface NavigationCommand {

    data object NavigateUp : NavigationCommand

    data class NavigateToRoute(val route: NavigationRoute, val options: NavOptions? = null) :
        NavigationCommand

    data class PopUpToRoute(
        val route: NavigationRoute,
        val inclusive: Boolean,
        val fallBackRoute: NavigationRoute,
    ) : NavigationCommand
}
