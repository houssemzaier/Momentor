package com.bravedroid.momentor.libraries.infrastructure.api.impl.concurrency

import com.bravedroid.momentor.libraries.infrastructure.api.concurrency.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppCoroutineDispatchersImpl @Inject constructor(
) : AppCoroutineDispatchers {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val computation: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}
