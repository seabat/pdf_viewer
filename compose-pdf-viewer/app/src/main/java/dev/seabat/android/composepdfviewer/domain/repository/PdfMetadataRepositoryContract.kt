package dev.seabat.android.composepdfviewer.domain.repository

interface PdfMetadataRepositoryContract {
    fun extractPdfTitle(pdfFilePath: String): String?
}