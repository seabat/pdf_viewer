package dev.seabat.android.composepdfviewer.ui.screen.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.usecase.FetchAllListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.UseCaseResult
import dev.seabat.android.composepdfviewer.ui.UiStateType
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
    private val fetchAllListUseCase: FetchAllListUseCaseContract
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllListUiState())
    val uiState: StateFlow<AllListUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    override fun onCleared() {
        fetchJob?.cancel()
        super.onCleared()
    }

    fun reload() {
        _uiState.update {
            it.copy(
                state = UiStateType.Loading,
                pdfs = PdfListEntity(arrayListOf())
            )
        }
        fetchJob?.cancel()
        fetch()
    }

    private fun fetch() {
        fetchJob = viewModelScope.launch {
            delay(1000)
            when (val result =fetchAllListUseCase()) {
                is UseCaseResult.Success -> {
                    _uiState.update {
                        it.copy(
                            state = UiStateType.Loaded,
                            pdfs = result.data
                        )
                    }
                }
                is UseCaseResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            state = UiStateType.Error(result.e),
                        )
                    }
                }
            }
        }
    }

}
