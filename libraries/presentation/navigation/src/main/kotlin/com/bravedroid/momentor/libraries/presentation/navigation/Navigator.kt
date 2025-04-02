package com.bravedroid.momentor.libraries.presentation.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


interface Navigator  {

      fun navigate(command: NavigationCommand)
}
