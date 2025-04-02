package com.bravedroid.momentor.presentation.features.profile

import com.bravedroid.momentor.data.model.Photo

sealed class PhotosState {
    data object Loading : PhotosState()
    data class Success(val photos: List<Photo>) : PhotosState()
    data class Error(val message: String) : PhotosState()
}
