package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentListUseCaseContract
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
    private val addRecentListUseCase: AddRecentListUseCaseContract
) : ViewModel() {
    private val _uiState = MutableStateFlow<PdfViewerUiState>(
        PdfViewerUiState(
            state = ScreenStateType.Loading,
            currentPageNo = 0,
            bitmap = null,
            zoom = ZoomType.ZoomNone
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

    fun addRecentPdf(pdf: PdfEntity) {
        viewModelScope.launch {
            addRecentListUseCase(pdf)
        }
    }

    fun extractPageCount(filePath: String): Int {
        val file = File(filePath)
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

    fun readAhead(pageNo: Int, displayArea: Dimensions) {
        doRendering(pageNo, displayArea, uiState.value.zoom)
    }

    fun changePageSize(displayArea: Dimensions) {
        val newZoom = ZoomType.next(uiState.value.zoom)
        doRendering(uiState.value.currentPageNo, displayArea, newZoom)
    }

    private fun doRendering(pageNo: Int, displayArea: Dimensions, zoomType: ZoomType) {
        renderingJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    state = ScreenStateType.Loading,
                )
            }

            val page = pdfRenderer.openPage(pageNo)
            val dimensions = calculateBitmapDimensions(
                Dimensions(page.width, page.height),
                displayArea
            )

            val bitmap = when (zoomType) {
                ZoomType.ZoomNone -> {
                    Bitmap.createBitmap(
                        dimensions.width,
                        dimensions.height,
                        Bitmap.Config.ARGB_8888
                    )
                }
                ZoomType.ZoomDouble -> {
                    Bitmap.createBitmap(
                        (dimensions.width * 2.0).toInt(),
                        (dimensions.height * 2.0).toInt(),
                        Bitmap.Config.ARGB_8888
                    )
                }
            }

            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            _uiState.update {
                it.copy(
                    currentPageNo = pageNo,
                    state = ScreenStateType.Loaded,
                    bitmap = bitmap,
                    zoom = zoomType
                )
            }

            page.close()
        }
    }

    private fun calculateBitmapDimensions(
        pdf: Dimensions,
        displayArea: Dimensions
    ): Dimensions {
        val pdfRatio = pdf.height.toDouble() / pdf.width.toDouble()
        val deviceRatio = displayArea.height.toDouble() / displayArea.width.toDouble()
        return if (deviceRatio > pdfRatio) {
            // 端末の方が縦長である場合は横幅を目一杯広げる
            // つまり、生成する Bitmap の width は表示領域と同じなり、
            // height には width の拡大/縮小した比率を適用する。
            Dimensions(
                width = displayArea.width,
                height = ((displayArea.width.toDouble() / pdf.width.toDouble()) * pdf.height).toInt()
            )
        } else {
            Dimensions(
                width = ((displayArea.height.toDouble() / pdf.height.toDouble()) * pdf.width).toInt(),
                height = displayArea.height
            )
        }
    }
}
