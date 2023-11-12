package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity

interface DeleteFileUseCaseContract {
    suspend operator fun invoke(pdf: PdfEntity)
}