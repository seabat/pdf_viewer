package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity

interface FetchRecentListUseCaseContract {
    suspend operator fun invoke(): UseCaseResult<PdfListEntity>
}