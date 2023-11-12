package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity

interface ImportSampleUseCaseContract {
    suspend operator fun invoke(): PdfEntity
}