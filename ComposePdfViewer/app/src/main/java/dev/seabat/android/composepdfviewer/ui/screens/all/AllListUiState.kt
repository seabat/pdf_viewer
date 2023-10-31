package dev.seabat.android.composepdfviewer.ui.screens.all

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType

data class AllListUiState(
    val state: ScreenStateType = ScreenStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)
