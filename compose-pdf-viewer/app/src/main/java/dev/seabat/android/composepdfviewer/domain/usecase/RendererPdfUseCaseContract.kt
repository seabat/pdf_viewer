package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.Dimensions
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.ZoomType

interface RendererPdfUseCaseContract {
    suspend operator fun invoke(
        renderer: PdfRenderer,
        pageNo: Int,
        displayArea: Dimensions,
        zoomType: ZoomType
    ): Bitmap
}