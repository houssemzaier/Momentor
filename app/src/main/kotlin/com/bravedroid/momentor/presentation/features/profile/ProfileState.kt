package com.bravedroid.momentor.presentation.features.profile

import com.bravedroid.momentor.data.model.User

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
