package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer

interface ExtractPageCountUseCaseContract {
    operator fun invoke(renderer: PdfRenderer): Int
}