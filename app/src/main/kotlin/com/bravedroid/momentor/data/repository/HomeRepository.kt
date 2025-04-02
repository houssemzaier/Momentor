package com.bravedroid.momentor.data.repository

import com.bravedroid.momentor.data.model.HomeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton



interface HomeRepository {
    fun getHomeItems(forceRefresh: Boolean = false): Flow<List<HomeItem>>
}

@Singleton
class FakeHomeRepository @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository
) : HomeRepository {

    override fun getHomeItems(forceRefresh: Boolean): Flow<List<HomeItem>> {
        // Fetch data based on forceRefresh parameter
        val photosFlow = photoRepository.getAllPhotos(forceRefresh)
        val user1Flow = userRepository.getUserById("user1", forceRefresh)
        val user2Flow = userRepository.getUserById("user2", forceRefresh)
        val user3Flow = userRepository.getUserById("user3", forceRefresh)

        // Combine all flows to create the home items
        return combine(
            photosFlow,
            user1Flow,
            user2Flow,
            user3Flow
        ) { photos, user1, user2, user3 ->
            val items = mutableListOf<HomeItem>()
            val userMap = mapOf(
                "user1" to user1,
                "user2" to user2,
                "user3" to user3
            )

            // Add a highlight for the most liked photo
            val mostLikedPhoto = photos.maxByOrNull { it.likes }
            mostLikedPhoto?.let {
                items.add(HomeItem.Highlight(it))
            }

            // Add recommendation
            items.add(HomeItem.Recommendation(user2))

            // Add recent activity
            photos.sortedByDescending { it.createdAt }.take(3).forEach { photo ->
                val author = userMap[photo.authorId]
                author?.let {
                    items.add(HomeItem.RecentActivity(photo, it))
                }
            }

            items
        }
    }
}
