package dev.seabat.android.composepdfviewer.ui.screen.pdfviewer

import dev.seabat.android.composepdfviewer.ui.UiStateType

data class PdfViewerUiState (
    val state: UiStateType = UiStateType.Loading,
    val totalPageCount: Int = 1,
    val currentPageNo: Int = 0
)