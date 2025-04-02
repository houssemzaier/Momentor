package com.bravedroid.momentor.libraries.infrastructure.api.impl.concurrency

import com.bravedroid.momentor.libraries.infrastructure.api.concurrency.AppCoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DiModule {
    @Binds
    @Singleton
    fun bindAppCoroutineDispatchers(
        impl: AppCoroutineDispatchersImpl
    ): AppCoroutineDispatchers

    companion object {
        @Provides
        fun createAppScope(): CoroutineScope {
            val supervisorJob = SupervisorJob()
            return CoroutineScope(Dispatchers.IO + supervisorJob)
        }
    }
}
