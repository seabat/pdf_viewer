package dev.seabat.android.composepdfviewer.screen.recentness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentnessViewModel @Inject constructor(
) : ViewModel() {
    sealed class UiStateType(val value: Int) {
        object Loading : UiStateType(0)
        object Loaded : UiStateType(1)
        data class Error(val e: Exception) : UiStateType(2)
    }

    data class UiState(
        val state: UiStateType = UiStateType.Loading,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    override fun onCleared() {
        fetchJob?.cancel()
        super.onCleared()
    }

    private fun fetch() {
        fetchJob = viewModelScope.launch {
            delay(1000)
            _uiState.update {
                it.copy(
                    state = UiStateType.Loaded
                )
            }
        }
    }
}