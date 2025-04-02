package com.bravedroid.momentor.libraries.presentation.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

interface NavigatorAccessor {
    fun setMainNavHostController(
        navigationController: NavHostController
    )

    fun clear()
}
