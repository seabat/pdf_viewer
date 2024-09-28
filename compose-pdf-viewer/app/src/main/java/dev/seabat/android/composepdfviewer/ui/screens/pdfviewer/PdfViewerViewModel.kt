package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.graphics.pdf.PdfRenderer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ClosePdfRendererUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.CreatePdfRendererUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPageCountUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.RendererPdfUseCaseContract
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PdfViewerViewModel
@Inject
constructor(
    private val addRecentListUseCase: AddRecentListUseCaseContract,
    private val closePdfRendererUseCase: ClosePdfRendererUseCaseContract,
    private val createPdfRendererUseCase: CreatePdfRendererUseCaseContract,
    private val extractPageCountUseCase: ExtractPageCountUseCaseContract,
    private val rendererPdfUseCase: RendererPdfUseCaseContract
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            PdfViewerUiState(
                state = ScreenStateType.Loading,
                currentPageIndex = 0,
                currentBitmap = null,
                zoom = ZoomType.ZoomNone,
                totalPageCount = 0
            )
        )
    val uiState: StateFlow<PdfViewerUiState> = _uiState.asStateFlow()

    private val _renderedBitmaps = MutableStateFlow(RenderedBitmapList(5))
    val renderedBitmaps: StateFlow<RenderedBitmapList> = _renderedBitmaps.asStateFlow()

    private var renderingJob: Job? = null
    private lateinit var pdfRenderer: PdfRenderer

    override fun onCleared() {
        renderingJob?.cancel()
        closePdfRendererUseCase(pdfRenderer)
        super.onCleared()
    }

    fun addRecentPdf(pdf: PdfResourceEntity) {
        viewModelScope.launch {
            addRecentListUseCase(pdf)
        }
    }

    fun createRendererAndExtractPageCount(filePath: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    state = ScreenStateType.Loading
                )
            }
            pdfRenderer = createPdfRendererUseCase(filePath)

            _uiState.update {
                it.copy(
                    state = ScreenStateType.Loaded,
                    totalPageCount = extractPageCountUseCase(pdfRenderer)
                )
            }
        }
    }

    fun readAhead(
        pageIndex: Int,
        displayArea: Dimensions
    ) {
        doRendering(pageIndex, displayArea, uiState.value.zoom)
    }

    fun changePageSize(displayArea: Dimensions) {
        val newZoom = ZoomType.next(uiState.value.zoom)
        doRendering(uiState.value.currentPageIndex, displayArea, newZoom)
    }

    private fun doRendering(
        pageIndex: Int,
        displayArea: Dimensions,
        zoomType: ZoomType
    ) {
        // 既にレンダリング済みの Bitmap が存在する場合はレンダリングをスキップする
        _renderedBitmaps.value.get(pageIndex)?.let { indexedBitmap ->
            _renderedBitmaps.update {
                it.apply { add(indexedBitmap) }
            }
            return@doRendering
        }

        renderingJob =
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        state = ScreenStateType.Loading
                    )
                }

                // TODO: あとで削除する
                delay(1000)

                val bitmap = rendererPdfUseCase(pdfRenderer, pageIndex, displayArea, zoomType)

                _renderedBitmaps.update {
                    it.apply { add(RenderedBitmap(pageIndex, bitmap)) }
                }
                _uiState.update {
                    it.copy(
                        currentPageIndex = pageIndex,
                        state = ScreenStateType.Loaded,
                        currentBitmap = bitmap,
                        zoom = zoomType
                    )
                }
            }
    }
}
