package dev.seabat.android.composepdfviewer.ui.screens.recentness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentnessListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.UseCaseResult
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
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
    private val fetchRecentnessListUseCase: FetchRecentnessListUseCaseContract
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecentnessUiState())
    val uiState: StateFlow<RecentnessUiState> = _uiState.asStateFlow()

    private var reloadJob: Job? = null

    override fun onCleared() {
        reloadJob?.cancel()
        super.onCleared()
    }

    fun reload() {
        reloadJob?.cancel()
        reloadJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    state = ScreenStateType.Loading,
                    pdfs = PdfListEntity(mutableListOf())
                )
            }
            delay(500)
            fetch()
        }
    }

    private suspend fun fetch() {
        when (val result =fetchRecentnessListUseCase()) {
            is UseCaseResult.Success -> {
                _uiState.update {
                    it.copy(
                        state = ScreenStateType.Loaded,
                        pdfs = result.data
                    )
                }
            }
            is UseCaseResult.Failure -> {
                _uiState.update {
                    it.copy(
                        state = ScreenStateType.Error(result.e),
                    )
                }
            }
        }
    }
}