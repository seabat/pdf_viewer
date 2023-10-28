package dev.seabat.android.composepdfviewer.ui.screens.pdfviewer

import dev.seabat.android.composepdfviewer.ui.UiStateType
import android.graphics.Bitmap

data class PdfViewerUiState (
    val state: UiStateType = UiStateType.Loading,
    val currentPageNo: Int = 0,
    val bitmap: Bitmap? = null,
    val zoom: ZoomType = ZoomType.ZoomNone
)

sealed class ZoomType(val value: Int) {
    companion object {
        fun next(zoomType: ZoomType): ZoomType {
            return when (zoomType) {
               is ZoomNone -> ZoomDouble
               is ZoomDouble -> ZoomNone
            }
        }
    }

    object ZoomNone : ZoomType(0)
    object ZoomDouble : ZoomType(1)
}