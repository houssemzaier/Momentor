package com.bravedroid.momentor.data.repository

import com.bravedroid.momentor.data.model.Photo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

interface PhotoRepository {
    fun getAllPhotos(forceRefresh: Boolean = false): Flow<List<Photo>>
    fun getPhotoById(id: String, forceRefresh: Boolean = false): Flow<Result<Photo>>
    fun getPhotosByUserId(userId: String, forceRefresh: Boolean = false): Flow<List<Photo>>
    suspend fun addPhoto(photo: Photo): Result<Photo>
    suspend fun likePhoto(photoId: String): Result<Photo>
}

@Singleton
class FakePhotoRepository @Inject constructor() : PhotoRepository {

    // Use Date object for timestamps as in our model
    private val photos = MutableStateFlow(
        listOf(
            Photo(
                id = "photo1",
                title = "Sunset at the beach",
                description = "Beautiful sunset captured at the beach",
                imageUrl = "https://example.com/photo1.jpg",
                authorId = "user1",
                likes = 42,
                createdAt = Date(System.currentTimeMillis() - 86400000).time // 1 day ago
            ),
            Photo(
                id = "photo2",
                title = "Mountain view",
                description = "Stunning mountain landscape",
                imageUrl = "https://example.com/photo2.jpg",
                authorId = "user2",
                likes = 28,
                createdAt = Date(System.currentTimeMillis() - 172800000).time // 2 days ago
            ),
            Photo(
                id = "photo3",
                title = "City skyline",
                description = "Urban architecture during sunset",
                imageUrl = "https://example.com/photo3.jpg",
                authorId = "user3",
                likes = 35,
                createdAt = Date(System.currentTimeMillis() - 259200000).time // 3 days ago
            ),
            Photo(
                id = "photo4",
                title = "Forest path",
                description = "Peaceful walk through the woods",
                imageUrl = "https://example.com/photo4.jpg",
                authorId = "user1",
                likes = 19,
                createdAt = Date(System.currentTimeMillis() - 345600000).time // 4 days ago
            )
        )
    )

    // This would be called when we want to simulate fetching from the network
    private suspend fun fetchPhotosFromNetwork(): List<Photo> {
        // Simulate network delay
        delay(1000)

        // In a real implementation, this would make an API call
        // For now, we'll return the same data with a slight modification to simulate "fresh" data
        return photos.value.map {
            it.copy(
                // We can modify some value to show that the data is "fresh"
                likes = it.likes + (1..5).random()
            )
        }
    }

    override fun getAllPhotos(forceRefresh: Boolean): Flow<List<Photo>> = flow {
        // Emit cached data first (if available)
        emit(photos.value)

        // If forceRefresh is true, fetch from network
        if (forceRefresh) {
            try {
                val networkPhotos = fetchPhotosFromNetwork()
                photos.value = networkPhotos // Update the cache
                emit(networkPhotos)
            } catch (e: Exception) {
                // In case of error, we've already emitted the cached data
                // Could log the error here
            }
        }
    }

    override fun getPhotoById(id: String, forceRefresh: Boolean): Flow<Result<Photo>> = flow {
        // First try to get from cache
        val cachedPhoto = photos.value.find { it.id == id }

        if (cachedPhoto != null) {
            emit(Result.success(cachedPhoto))
        }

        // If forceRefresh or not found in cache, try network
        if (forceRefresh || cachedPhoto == null) {
            try {
                delay(500) // Simulate network request
                val photo = photos.value.find { it.id == id }
                    ?: throw NoSuchElementException("Photo with id $id not found")
                emit(Result.success(photo))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getPhotosByUserId(userId: String, forceRefresh: Boolean): Flow<List<Photo>> = flow {
        // Emit cached data first
        emit(photos.value.filter { it.authorId == userId })

        // If forceRefresh, fetch from network
        if (forceRefresh) {
            try {
                val networkPhotos = fetchPhotosFromNetwork()
                val userPhotos = networkPhotos.filter { it.authorId == userId }
                // Update the cache with new data
                photos.value = networkPhotos
                emit(userPhotos)
            } catch (e: Exception) {
                // In case of error, we've already emitted the cached data
            }
        }
    }

    override suspend fun addPhoto(photo: Photo): Result<Photo> {
        return try {
            val currentPhotos = photos.value.toMutableList()
            currentPhotos.add(0, photo) // Add to beginning of list
            photos.value = currentPhotos
            Result.success(photo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun likePhoto(photoId: String): Result<Photo> {
        return try {
            val currentPhotos = photos.value.toMutableList()
            val photoIndex = currentPhotos.indexOfFirst { it.id == photoId }

            if (photoIndex != -1) {
                val photo = currentPhotos[photoIndex]
                val updatedPhoto = photo.copy(likes = photo.likes + 1)
                currentPhotos[photoIndex] = updatedPhoto
                photos.value = currentPhotos
                Result.success(updatedPhoto)
            } else {
                Result.failure(NoSuchElementException("Photo with id $photoId not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
