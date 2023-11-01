package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity

interface ExtractPdfTitleUseCaseContract {
    operator fun invoke(pdfFilePath: String): String?
}