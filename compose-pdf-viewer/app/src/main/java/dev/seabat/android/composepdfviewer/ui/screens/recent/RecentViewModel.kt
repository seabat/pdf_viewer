package dev.seabat.android.composepdfviewer.ui.screens.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.usecase.AddFavoriteUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.DeleteFileUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentListUseCaseContract
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
class RecentViewModel
@Inject
constructor(
    private val addFavoriteUseCase: AddFavoriteUseCaseContract,
    private val deleteFileUseCase: DeleteFileUseCaseContract,
    private val fetchRecentListUseCase: FetchRecentListUseCaseContract
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecentUiState())
    val uiState: StateFlow<RecentUiState> = _uiState.asStateFlow()

    private var reloadJob: Job? = null
    private var addJob: Job? = null

    override fun onCleared() {
        reloadJob?.cancel()
        addJob?.cancel()
        super.onCleared()
    }

    fun reload() {
        reloadJob?.cancel()
        reloadJob =
            viewModelScope.launch {
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

    fun addFavorite(pdf: PdfEntity) {
        addJob =
            viewModelScope.launch {
                addFavoriteUseCase(pdf)
            }
    }

    fun deletePdfFile(pdf: PdfEntity) {
        viewModelScope.launch {
            deleteFileUseCase(pdf)
        }
    }

    private suspend fun fetch() {
        when (val result = fetchRecentListUseCase()) {
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
                        state = ScreenStateType.Error(result.e)
                    )
                }
            }
        }
    }
}
