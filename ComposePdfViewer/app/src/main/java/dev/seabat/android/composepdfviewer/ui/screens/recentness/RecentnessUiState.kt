package dev.seabat.android.composepdfviewer.ui.screens.recentness

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.ui.UiStateType

data class RecentnessUiState(
    val state: UiStateType = UiStateType.Loading,
    val pdfs: PdfListEntity = PdfListEntity(arrayListOf())
)

