package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.seabat.android.composepdfviewer.ui.UiStateType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PdfViewerViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow<PdfViewerUiState>(
        PdfViewerUiState(
            state = UiStateType.Loading,
            totalPageCount = 0,
            currentPageNo = 0
        )
    )
    val uiState: StateFlow<PdfViewerUiState> = _uiState.asStateFlow()

    private var renderingJob: Job? = null

//    init {
//        doRendering(1)
//    }

    override fun onCleared() {
        renderingJob?.cancel()
        super.onCleared()
    }

    fun readAhead(pageNo: Int) {
        doRendering(pageNo)
    }

    private fun doRendering(pageNo: Int) {
        renderingJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    state = UiStateType.Loading,
                )
            }

            delay(1000)

            _uiState.update {
                PdfViewerUiState(
                    state = UiStateType.Loaded,
                    totalPageCount = 10,
                    currentPageNo = pageNo
                )
            }
        }
    }
}