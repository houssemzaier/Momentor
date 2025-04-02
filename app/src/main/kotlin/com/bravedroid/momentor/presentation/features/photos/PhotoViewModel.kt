package com.bravedroid.momentor.presentation.features.photos
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravedroid.momentor.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PhotoUiState>(PhotoUiState.Loading)
    val uiState: StateFlow<PhotoUiState> = _uiState.asStateFlow()

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            getPhotosUseCase()
                .catch { e ->
                    _uiState.value = PhotoUiState.Error(e.message ?: "Unknown error")
                }
                .collect { photos ->
                    _uiState.value = PhotoUiState.Success(photos)
                }
        }
    }

    fun refresh() {
        _uiState.value = PhotoUiState.Loading
        loadPhotos()
    }
}
