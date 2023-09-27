package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.AllListRepositoryContract
import javax.inject.Inject

class FetchAllListUseCase @Inject constructor(
    private val allListRepository: AllListRepositoryContract
) : FetchAllListUseCaseContract {
    override suspend fun invoke(): UseCaseResult<PdfListEntity> {
        return try {
            val list = allListRepository.fetch()
            UseCaseResult.Success(list)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}