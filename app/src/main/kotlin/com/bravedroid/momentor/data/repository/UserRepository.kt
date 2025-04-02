package com.bravedroid.momentor.data.repository

import com.bravedroid.momentor.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    fun getAllUsers(forceRefresh: Boolean = false): Flow<List<User>>
    fun getCurrentUser(forceRefresh: Boolean = false): Flow<Result<User>>
    fun getUserById(id: String, forceRefresh: Boolean = false): Flow<User>
    suspend fun updateUserProfile(user: User): Result<User>
}

@Singleton
class FakeUserRepository @Inject constructor() : UserRepository {

    private val currentUser = MutableStateFlow(
        User(
            id = "user1",
            name = "John Doe",
            bio = "Photography enthusiast",
            avatarUrl = "https://example.com/avatar1.jpg"
        )
    )

    private val usersFlow = MutableStateFlow(
        mapOf(
            "user1" to currentUser.value,
            "user2" to User(
                id = "user2",
                name = "Jane Smith",
                bio = "Travel photographer",
                avatarUrl = "https://example.com/avatar2.jpg"
            ),
            "user3" to User(
                id = "user3",
                name = "Alex Johnson",
                bio = "Nature lover",
                avatarUrl = "https://example.com/avatar3.jpg"
            )
        )
    )

    // Simulate network fetch
    private suspend fun fetchUserFromNetwork(userId: String): User? {
        // Simulate network delay
        delay(800)

        // In a real implementation, this would make an API call
        return usersFlow.value[userId]
    }

    // Simulate network fetch for all users
    private suspend fun fetchAllUsersFromNetwork(): List<User> {
        // Simulate network delay
        delay(1200)

        // In a real implementation, this would make an API call
        return usersFlow.value.values.toList()
    }

    override fun getAllUsers(forceRefresh: Boolean): Flow<List<User>> = flow {
        // Emit cached data first
        emit(usersFlow.value.values.toList())

        // If forceRefresh, fetch from network
        if (forceRefresh) {
            try {
                val networkUsers = fetchAllUsersFromNetwork()
                // Update cache with new data
                val updatedMap = networkUsers.associateBy { it.id }
                usersFlow.value = updatedMap
                emit(networkUsers)
            } catch (e: Exception) {
                // Already emitted cached data, could log error here
            }
        }
    }

    override fun getCurrentUser(forceRefresh: Boolean): Flow<Result<User>> = flow {
        // Emit current user from cache first
        emit(Result.success(currentUser.value))

        // If forceRefresh, fetch from network
        if (forceRefresh) {
            try {
                // In a real app, this would be a network call to get fresh user data
                val freshUser = fetchUserFromNetwork(currentUser.value.id)

                if (freshUser != null) {
                    // Update the current user
                    currentUser.value = freshUser
                    // Update the users map
                    val updatedMap = usersFlow.value.toMutableMap()
                    updatedMap[freshUser.id] = freshUser
                    usersFlow.value = updatedMap

                    emit(Result.success(freshUser))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getUserById(id: String, forceRefresh: Boolean): Flow<User> = flow {
        // Emit cached user first if available
        val cachedUser = usersFlow.value[id]
        if (cachedUser != null) {
            emit(cachedUser)
        } else {
            // Create fallback user if not found
            val fallbackUser = User(
                id = id,
                name = "Unknown User",
                bio = "No bio available",
                avatarUrl = "https://example.com/default-avatar.jpg"
            )
            emit(fallbackUser)
        }

        // If forceRefresh, fetch from network
        if (forceRefresh) {
            try {
                val freshUser = fetchUserFromNetwork(id)

                if (freshUser != null) {
                    // Update cache
                    val updatedMap = usersFlow.value.toMutableMap()
                    updatedMap[id] = freshUser
                    usersFlow.value = updatedMap

                    emit(freshUser)
                }
            } catch (e: Exception) {
                // Already emitted cached or fallback user
            }
        }
    }

    override suspend fun updateUserProfile(user: User): Result<User> {
        return try {
            // Update current user if it's the same ID
            if (user.id == currentUser.value.id) {
                currentUser.value = user
            }

            // Update users map
            val updatedMap = usersFlow.value.toMutableMap()
            updatedMap[user.id] = user
            usersFlow.value = updatedMap

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
