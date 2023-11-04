package dev.seabat.android.composepdfviewer.ui.screens.recent

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType

data class RecentUiState(
    val state: ScreenStateType = ScreenStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)

