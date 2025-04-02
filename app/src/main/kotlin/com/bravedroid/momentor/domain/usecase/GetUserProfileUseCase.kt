package com.bravedroid.momentor.domain.usecase

import com.bravedroid.momentor.data.model.User
import com.bravedroid.momentor.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke()  = userRepository.getCurrentUser()
}
