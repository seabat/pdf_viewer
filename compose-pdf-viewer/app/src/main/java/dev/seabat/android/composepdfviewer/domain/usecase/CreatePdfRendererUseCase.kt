package dev.seabat.android.composepdfviewer.domain.usecase

import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.IOException
import javax.inject.Inject

class CreatePdfRendererUseCase @Inject constructor() : CreatePdfRendererUseCaseContract {
    override suspend fun invoke(filePath: String): PdfRenderer {
        val file = File(filePath)
        return try {
            val parcelFileDescriptor = ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_READ_ONLY
            )
            PdfRenderer(parcelFileDescriptor)
        } catch (e: IOException) {
            // T0DO:
            throw e
        }
    }
}