package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer

interface CreatePdfRendererUseCaseContract {
    suspend operator fun invoke(filePath: String): PdfRenderer
}