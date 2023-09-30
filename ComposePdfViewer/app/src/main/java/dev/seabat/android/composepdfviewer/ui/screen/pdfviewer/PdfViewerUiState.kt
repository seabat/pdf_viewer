package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import dev.seabat.android.composepdfviewer.ui.UiStateType
import android.graphics.Bitmap

data class PdfViewerUiState (
    val state: UiStateType = UiStateType.Loading,
    val currentPageNo: Int = 0,
    val bitmap: Bitmap? = null
)