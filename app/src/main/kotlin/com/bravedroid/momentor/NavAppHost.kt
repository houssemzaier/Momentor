package com.bravedroid.momentor

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bravedroid.momentor.libraries.presentation.designsystem.components.BottomNavBar
import com.bravedroid.momentor.libraries.presentation.navigation.NavigationRoute
import com.bravedroid.momentor.libraries.presentation.navigation.NavigatorAccessor
import com.bravedroid.momentor.presentation.features.home.HomeScreen
import com.bravedroid.momentor.presentation.features.photos.PhotosScreen
import com.bravedroid.momentor.presentation.features.profile.ProfileScreen

@Composable
fun NavAppHost(navigatorAccessor: NavigatorAccessor) {
    val navController: NavHostController = rememberNavController()

    DisposableEffect(navController) {
        navigatorAccessor.setMainNavHostController(navController)
        onDispose {
            navigatorAccessor.clear()
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavBar(
                    routeList = NavigationRoute.bottomRouteList,
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { -it } },
            popEnterTransition = { slideInHorizontally { -it } },
            popExitTransition = { slideOutHorizontally { it } },
        ) {
            composable(NavigationRoute.Home.route) {
                HomeScreen()
            }
            composable(NavigationRoute.Photos.route) {
                PhotosScreen()
            }
            composable(NavigationRoute.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
