package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract
): DeleteFileUseCaseContract {
    override suspend fun invoke(pdf: PdfEntity) {
        localFileRepository.remove(pdf.fileName)
    }
}