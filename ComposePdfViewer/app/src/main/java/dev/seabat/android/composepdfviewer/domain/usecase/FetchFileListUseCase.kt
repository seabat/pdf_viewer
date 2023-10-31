package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import javax.inject.Inject

class FetchFileListUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract
) : FetchFileListUseCaseContract {
    override suspend fun invoke(): UseCaseResult<PdfListEntity> {
        return try {
            val list = localFileRepository.fetch()
            UseCaseResult.Success(list)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}