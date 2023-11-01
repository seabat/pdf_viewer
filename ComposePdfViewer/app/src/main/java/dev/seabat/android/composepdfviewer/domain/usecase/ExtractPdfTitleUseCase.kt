package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.repository.PdfMetadataRepositoryContract
import javax.inject.Inject

class ExtractPdfTitleUseCase @Inject constructor(
    private val pdfMetaDataRepository: PdfMetadataRepositoryContract
) : ExtractPdfTitleUseCaseContract {
    override fun invoke(pdfFilePath: String): String? {
        return pdfMetaDataRepository.extractPdfTitle(pdfFilePath)
    }
}