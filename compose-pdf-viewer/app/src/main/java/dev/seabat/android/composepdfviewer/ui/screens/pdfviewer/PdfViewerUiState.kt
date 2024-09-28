package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import android.graphics.Bitmap
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType

data class PdfViewerUiState(
    val state: ScreenStateType = ScreenStateType.Loading,
    val currentPageIndex: Int = 0,
    val currentBitmap: Bitmap? = null,
    val zoom: ZoomType = ZoomType.ZoomNone,
    val totalPageCount: Int = 0
)

sealed class ZoomType(val value: Int) {
    companion object {
        fun next(zoomType: ZoomType): ZoomType = when (zoomType) {
            is ZoomNone -> ZoomDouble
            is ZoomDouble -> ZoomNone
        }
    }

    object ZoomNone : ZoomType(0)
    object ZoomDouble : ZoomType(1)
}