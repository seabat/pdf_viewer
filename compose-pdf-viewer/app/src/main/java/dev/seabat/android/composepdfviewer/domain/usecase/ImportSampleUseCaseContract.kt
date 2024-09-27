package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity

interface ImportSampleUseCaseContract {
    suspend operator fun invoke(): PdfResourceEntity
}