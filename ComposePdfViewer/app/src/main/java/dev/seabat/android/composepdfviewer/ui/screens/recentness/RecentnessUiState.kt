package dev.seabat.android.composepdfviewer.ui.screens.recentness

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.screens.ScreenStateType

data class RecentnessUiState(
    val state: ScreenStateType = ScreenStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)

