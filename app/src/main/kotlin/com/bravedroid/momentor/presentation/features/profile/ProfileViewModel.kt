package com.bravedroid.momentor.presentation.features.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravedroid.momentor.domain.usecase.GetUserPhotosUseCase
import com.bravedroid.momentor.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserPhotosUseCase: GetUserPhotosUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _photosState = MutableStateFlow<PhotosState>(PhotosState.Loading)
    val photosState: StateFlow<PhotosState> = _photosState.asStateFlow()

    init {
        loadProfile()
        loadPhotos()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getUserProfileUseCase()
                .catch { e ->
                    _profileState.value = ProfileState.Error(e.message ?: "Unknown error")
                }
                .collect { user ->
                    _profileState.value = ProfileState.Success(user.getOrThrow())
                }
        }
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            try {
                getUserPhotosUseCase()
                    .catch { e ->
                        _photosState.value = PhotosState.Error(e.message ?: "Unknown error")
                    }
                    .collect { photos ->
                        _photosState.value = PhotosState.Success(photos)
                    }
            } catch (e: Exception) {
                _photosState.value = PhotosState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refresh() {
        _profileState.value = ProfileState.Loading
        _photosState.value = PhotosState.Loading
        loadProfile()
        loadPhotos()
    }
}

