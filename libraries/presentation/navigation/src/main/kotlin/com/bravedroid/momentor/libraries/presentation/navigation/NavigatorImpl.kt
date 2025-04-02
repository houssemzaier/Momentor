package com.bravedroid.momentor.libraries.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bravedroid.momentor.libraries.infrastructure.api.concurrency.AppCoroutineDispatchers
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NavigatorImpl @Inject constructor(coroutineDispatchers: AppCoroutineDispatchers) : Navigator,
    NavigatorAccessor {

    private var navControllerFlow = MutableStateFlow<NavController?>(null)
    private val scope by lazy { CoroutineScope(SupervisorJob() + coroutineDispatchers.main) }

    override fun setMainNavHostController(navigationController: NavHostController) {
        this.navControllerFlow.update { navigationController }
    }

    override fun navigate(command: NavigationCommand) {
        scope.launch {
            getNavController().handleComposeNavigationCommand(command = command)
        }
    }

    override fun clear() {
        this.navControllerFlow.update { null }
    }

    private suspend fun getNavController() = navControllerFlow.mapNotNull { it }.first()

    private fun NavController.handleComposeNavigationCommand(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.NavigateToRoute -> {
                navigate(command.route, command.options)
            }

            NavigationCommand.NavigateUp -> navigateUp()

            is NavigationCommand.PopUpToRoute -> {
                val hasPopped = popBackStack(
                    command.route,
                    command.inclusive,
                )
                if (!hasPopped) {
                    popBackStack(
                        command.fallBackRoute,
                        command.inclusive,
                    )
                }
            }
        }
    }
}
