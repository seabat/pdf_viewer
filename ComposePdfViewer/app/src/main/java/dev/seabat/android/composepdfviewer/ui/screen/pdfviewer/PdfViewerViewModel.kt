package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.seabat.android.composepdfviewer.ui.UiStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PdfViewerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<PdfViewerUiState>(
        PdfViewerUiState(
            state = UiStateType.Loading,
            currentPageNo = 0,
            bitmap = null
        )
    )
    val uiState: StateFlow<PdfViewerUiState> = _uiState.asStateFlow()

    private var totalPageCount = 0
    private var renderingJob: Job? = null
    private lateinit var pdfRenderer: PdfRenderer

    override fun onCleared() {
        renderingJob?.cancel()
        super.onCleared()
    }

    fun extractPageCount(): Int {
        val file = File(context.filesDir, "sample.pdf")
        try {
            val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(parcelFileDescriptor)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return pdfRenderer.pageCount.let {
            totalPageCount = it
            totalPageCount
        }
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

            val page = pdfRenderer.openPage(pageNo)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            _uiState.update {
                PdfViewerUiState(
                    state = UiStateType.Loaded,
                    currentPageNo = pageNo,
                    bitmap = bitmap
                )
            }

            page.close()
        }
    }
}