package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity

interface AddRecentListUseCaseContract {
    suspend operator fun invoke(pdf: PdfResourceEntity): UseCaseResult<PdfListEntity>
}