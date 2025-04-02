package com.bravedroid.momentor.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravedroid.momentor.data.model.HomeItem
import com.bravedroid.momentor.domain.usecase.GetHomeItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState(isLoading = true))
    val viewState: StateFlow<HomeViewState> = _viewState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>()
    val events: SharedFlow<HomeEvent> = _events.asSharedFlow()

    private val intents = MutableSharedFlow<HomeIntent>()

    init {
        processIntents()
        sendIntent(HomeIntent.LoadHomeItems)
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is HomeIntent.LoadHomeItems -> loadHomeItems()
                    is HomeIntent.RefreshHomeItems -> refreshHomeItems()
                }
            }
        }
    }

    private fun loadHomeItems() {
        viewModelScope.launch {
            // Update state to show loading
            _viewState.update { it.copy(isLoading = true, error = null) }

            getHomeItemsUseCase()
                .collect { result ->
                    result.fold(
                        onSuccess = { items ->
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    items = items,
                                    error = null
                                )
                            }
                        },
                        onFailure = { error ->
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = error.message ?: "Unknown error"
                                )
                            }
                            _events.emit(HomeEvent.ShowError(error.message ?: "Unknown error"))
                        }
                    )
                }
        }
    }

    private fun refreshHomeItems() {
        viewModelScope.launch {
            // Update state to show refreshing
            _viewState.update { it.copy(isRefreshing = true, error = null) }

            getHomeItemsUseCase(forceRefresh = true)
                .collect { result ->
                    result.fold(
                        onSuccess = { items ->
                            _viewState.update {
                                it.copy(
                                    isRefreshing = false,
                                    items = items,
                                    error = null
                                )
                            }
                            _events.emit(HomeEvent.RefreshComplete)
                        },
                        onFailure = { error ->
                            _viewState.update {
                                it.copy(
                                    isRefreshing = false,
                                    error = error.message ?: "Unknown error"
                                )
                            }
                            _events.emit(HomeEvent.ShowError(error.message ?: "Unknown error"))
                        }
                    )
                }
        }
    }


    // Intent - User actions
    sealed class HomeIntent {
        data object LoadHomeItems : HomeIntent()
        data object RefreshHomeItems : HomeIntent()
    }

    // State - UI state
    data class HomeViewState(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val items: List<HomeItem> = emptyList(),
        val error: String? = null
    )

    // Event - One-time events (like navigation, snackbars, etc.)
    sealed class HomeEvent {
        data class ShowError(val message: String) : HomeEvent()
        data object RefreshComplete : HomeEvent()
    }
}
