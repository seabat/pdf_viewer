package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer

interface ClosePdfRendererUseCaseContract {
    operator fun invoke(renderer: PdfRenderer)
}