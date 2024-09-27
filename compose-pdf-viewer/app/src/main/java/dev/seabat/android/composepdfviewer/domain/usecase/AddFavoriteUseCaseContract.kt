package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity

interface AddFavoriteUseCaseContract {
    suspend operator fun invoke(pdf: PdfResourceEntity)
}