package com.bravedroid.momentor.libraries.presentation.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DiModule {
    @Binds
    @Singleton
    fun bindNavigator(
        impl: NavigatorImpl
    ): Navigator

    @Binds
    @Singleton
    fun bindNavigatorAccessor(
        impl: NavigatorImpl
    ): NavigatorAccessor
}
