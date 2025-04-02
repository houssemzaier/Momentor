package com.bravedroid.momentor.domain.usecase

import com.bravedroid.momentor.data.model.HomeItem
import com.bravedroid.momentor.data.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHomeItemsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Result<List<HomeItem>>> {
        return homeRepository.getHomeItems(forceRefresh)
            .map { Result.success(it) }
            .catch { e -> emit(Result.failure(e)) }
    }
}
