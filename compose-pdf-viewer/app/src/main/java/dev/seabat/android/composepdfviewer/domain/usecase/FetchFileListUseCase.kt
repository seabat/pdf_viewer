package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import timber.log.Timber
import javax.inject.Inject

class FetchFileListUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract,
    private val extractPdfTitleUseCase: ExtractPdfTitleUseCaseContract
) : FetchFileListUseCaseContract {
    override suspend fun invoke(): UseCaseResult<PdfListEntity> {
        Timber.d("FetchFileListUseCase start")
        return try {
            val list = localFileRepository.fetch()
            val listWithPath = list.map {
                it.copy(
                    title = extractPdfTitleUseCase(it.pathString) ?: it.fileName
                )
            }
            Timber.d("FetchFileListUseCase end")
            UseCaseResult.Success(PdfListEntity(listWithPath.toMutableList()))
        } catch (e: Exception) {
            Timber.d("FetchFileListUseCase ${e.message}")
            UseCaseResult.Failure(e)
        }
    }
}