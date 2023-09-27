package dev.seabat.android.composepdfviewer.ui.uistate

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity

sealed class UiStateType(val value: Int) {
    object Loading : UiStateType(0)
    object Loaded : UiStateType(1)
    data class Error(val e: Exception) : UiStateType(2)
}

data class UiState(
    val state: UiStateType = UiStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(mutableListOf())
)

