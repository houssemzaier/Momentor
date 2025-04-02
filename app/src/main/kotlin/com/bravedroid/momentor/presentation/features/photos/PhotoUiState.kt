package com.bravedroid.momentor.presentation.features.photos

import com.bravedroid.momentor.data.model.Photo

sealed class PhotoUiState {
    data object Loading : PhotoUiState()
    data class Success(val photos: List<Photo>) : PhotoUiState()
    data class Error(val message: String) : PhotoUiState()
}
