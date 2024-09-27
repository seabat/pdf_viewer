package dev.seabat.android.composepdfviewer.domain.usecase

interface ExtractPdfTitleUseCaseContract {
    operator fun invoke(pdfFilePath: String): String?
}