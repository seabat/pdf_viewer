package dev.seabat.android.composepdfviewer.ui.screens.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentnessListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFileListUseCaseContract
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
class AllListViewModel @Inject constructor(
    private val fetchFileListUseCase: FetchFileListUseCaseContract,
    private val addRecentnessUseCase: AddRecentnessListUseCaseContract
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllListUiState())
    val uiState: StateFlow<AllListUiState> = _uiState.asStateFlow()

    private var initJob: Job? = null
    private var reloadJob: Job? = null
    private var addJob: Job? = null

    init {
        initJob = viewModelScope.launch {
            fetch()
        }
    }

    override fun onCleared() {
        initJob?.cancel()
        reloadJob?.cancel()
        addJob?.cancel()
        super.onCleared()
    }

    fun reload() {
        reloadJob = viewModelScope.launch {
            delay(1000)
            _uiState.update {
                it.copy(
                    state = ScreenStateType.Loading,
                    pdfs = PdfListEntity(mutableListOf())
                )
            }
            reloadJob?.cancel()
            fetch()
        }
    }

    fun addRecentness(pdf: PdfEntity, onAddCompleted: () -> Unit) {
        addJob = viewModelScope.launch {
            when (val result =addRecentnessUseCase(pdf)) {
                is UseCaseResult.Success -> {
                    onAddCompleted()
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

    private suspend fun fetch() {
        when (val result = fetchFileListUseCase()) {
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
