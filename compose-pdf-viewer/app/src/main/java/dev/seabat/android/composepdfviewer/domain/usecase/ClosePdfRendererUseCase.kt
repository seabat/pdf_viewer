package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer
import javax.inject.Inject

class ClosePdfRendererUseCase @Inject constructor() : ClosePdfRendererUseCaseContract {
    override fun invoke(renderer: PdfRenderer) {
        renderer.close()
    }
}