package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer
import javax.inject.Inject

class ExtractPageCountUseCase @Inject constructor() : ExtractPageCountUseCaseContract {
    override fun invoke(renderer: PdfRenderer): Int = renderer.pageCount
}