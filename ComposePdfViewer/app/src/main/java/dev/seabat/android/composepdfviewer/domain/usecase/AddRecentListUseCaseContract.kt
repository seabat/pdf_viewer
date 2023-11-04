package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity

interface AddRecentListUseCaseContract {
    suspend operator fun invoke(pdf: PdfEntity): UseCaseResult<PdfListEntity>
}