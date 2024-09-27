package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import javax.inject.Inject

class FetchRecentListUseCase @Inject constructor(
    private val recentListRepository: RecentListRepositoryContract
) : FetchRecentListUseCaseContract {
    override suspend operator fun invoke(): UseCaseResult<PdfListEntity> {
        return try {
            val recentList = recentListRepository.fetch()
            UseCaseResult.Success(recentList)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}