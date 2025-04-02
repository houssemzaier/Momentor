package com.bravedroid.momentor.domain.usecase

import com.bravedroid.momentor.data.model.Photo
import com.bravedroid.momentor.data.repository.PhotoRepository
import com.bravedroid.momentor.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetUserPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository
) {
    // This should return a flow directly instead of a suspend function returning a flow
    operator fun invoke(): Flow<List<Photo>> {
        // Use flatMapLatest to transform the user flow into a photos flow
        return userRepository.getCurrentUser().flatMapLatest { user ->
            photoRepository.getPhotosByUserId(user.getOrThrow().id, forceRefresh = false)
        }
    }
}
