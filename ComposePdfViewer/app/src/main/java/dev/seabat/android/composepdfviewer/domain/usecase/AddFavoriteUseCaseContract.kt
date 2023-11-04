package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity

interface AddFavoriteUseCaseContract {
    suspend operator fun invoke(pdf: PdfEntity)
}