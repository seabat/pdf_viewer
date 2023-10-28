package dev.seabat.android.composepdfviewer.ui.screens.all

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.UiStateType

data class AllListUiState(
    val state: UiStateType = UiStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)
