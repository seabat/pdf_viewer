package dev.seabat.android.composepdfviewer.ui.screens.favorite

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType

data class FavoriteUiState(
    val state: ScreenStateType = ScreenStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)
