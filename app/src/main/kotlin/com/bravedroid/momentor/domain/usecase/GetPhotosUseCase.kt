package com.bravedroid.momentor.domain.usecase

import com.bravedroid.momentor.data.model.Photo
import com.bravedroid.momentor.data.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    operator fun invoke(): Flow<List<Photo>> = photoRepository.getAllPhotos()
}
