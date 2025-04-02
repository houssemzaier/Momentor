package com.bravedroid.momentor.libraries.presentation.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bravedroid.libraries.presentation.translations.R
import com.bravedroid.momentor.libraries.presentation.designsystem.theme.MomentorTheme

@Composable
fun RowScope.BottomNavBar(
    routeList: List<String>,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBarItemStateList(routeList).forEach{ item ->
        val selected = currentRoute == item.route
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.title
                )
            },
            label = { Text(item.title) },
            selected = selected,
            onClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        )
    }
}

@Composable
private fun NavigationBarItemStateList(routeList: List<String>) = listOf(
    NavigationBarItemState(
        title = stringResource(id = R.string.bottomNavItem_home),
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = routeList[0]
    ),
    NavigationBarItemState(
        title = stringResource(id = R.string.bottomNavItem_photos),
        selectedIcon = Icons.Filled.PhotoLibrary,
        unselectedIcon = Icons.Outlined.PhotoLibrary,
        route = routeList[1]
    ),
    NavigationBarItemState(
        title = stringResource(id = R.string.bottomNavItem_profile),
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = routeList[2]
    )
)

@Preview
@Composable
private fun BottomNavItemsPreview() {
    MomentorTheme {
        Row {
            BottomNavBar(listOf("home", "photos", "profile"), "photos", rememberNavController())
        }
    }
}

data class NavigationBarItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
